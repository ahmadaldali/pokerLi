package com.api.planning.service;


import com.api.common.enums.SprintInclude;
import com.api.common.enums.SprintMembership;
import com.api.common.enums.UserRole;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.common.utils.Utils;
import com.api.event.publisher.UserStoryEventPublisher;
import com.api.planning.dto.response.sprint.SprintResponse;
import com.api.planning.dto.response.sprint.SprintResponseMapper;
import com.api.planning.dto.response.sprint.UserSprintsResponse;
import com.api.planning.dto.response.userstory.UserStoryResponse;
import com.api.planning.entity.Sprint;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.SprintRepository;
import com.api.user.dto.response.UserResponse;
import com.api.user.entity.User;
import com.api.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class SprintService {
  // repo
  private final SprintRepository sprintRepository;

  // services
  private final UserStoryService userStoryService;
  private final ParticipantService participantService;
  private final UserService userService;
  private final CardDeckService cardDeckService;
  private final UserStoryEventPublisher userStoryEventPublisher;

  // response
  private final SprintResponseMapper sprintResponseMapper;

  @PersistenceContext
  private EntityManager entityManager;
  private ObjectMapper objectMapper;

  @Transactional
  public SprintResponse create(String name, JsonNode cardDeck, List<Double> sequence, Long userId) {
    userService.ensureAdmin(userId);

    Sprint sprint = Sprint.builder()
      .name(name)
      .cardDeck(Utils.safeCopyJsonNode(cardDeck))
      .sequence(List.copyOf(cardDeckService.getSequence(cardDeck, sequence)))
      .creator(entityManager.getReference(User.class, userId))
      .build();

    validateSprintName(sprint);
    sprintRepository.save(sprint);

    return this.createSprintMember(sprint.getId(), userId);
  }

  public SprintResponse get(Long sprintId, Long userId, Set<String> include) {
    Set<SprintInclude> includes = SprintInclude.parse(include);

    Sprint sprint = this.getSprint(sprintId, userId, includes);

    return sprintResponseMapper.toResponse(sprint, includes);
  }

  public SprintResponse get(Long sprintId, Set<String> include) {
    Set<SprintInclude> includes = SprintInclude.parse(include);

    Sprint sprint = this.getSprint(sprintId, includes);

    return sprintResponseMapper.toResponse(sprint, includes);
  }

  public UserSprintsResponse getUserSprints(Long memberId, Set<String> membership, Set<String> include) {
    Set<SprintMembership> memberships = SprintMembership.parse(membership);
    Set<SprintInclude> includes = SprintInclude.parse(include);

    return this.getSprintsByMembership(memberId,  memberships, includes);
  }

  @Transactional
  public SprintResponse join(Long sprintId, Long memberId) {
    Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    if (participantService.isMember(memberId, sprintId)) {
      throw new ValidationException("error.sprint.already_member");
    }

    UserResponse member = userService.getUser(memberId);
    UserRole role = member.getRole();

    switch (role) {
      case MEMBER -> validateMemberAccess(member, sprint);
      case ADMIN -> validateAdminAccess(member, sprint);
      case GUEST -> { /* Guests join any sprint */ }
      default -> throw new ForbiddenException();
    }

    return this.createSprintMember(sprintId, memberId);
  }

  // ========================= sprint user-stories ==========================

  /**
   * Creates or updates a user story for a sprint.
   * A sprint can have only one active generic user story at a time.
   * Starting a new voting session is allowed only when no other active stories exist.
   */
  public UserStoryResponse createUserStory(
    Boolean isGenericUS,
    Long sprintId,
    Long userId,
    String name,
    String description,
    String link
  ) {
    // ensure sprint exists and user is a member
    Sprint sprint = getSprint(sprintId, userId, Set.of());

    boolean hasActiveGenericUS = userStoryService.hasActiveGenericUserStory(sprintId);
    boolean hasActiveUS = userStoryService.hasActiveUserStory(sprintId);

    // case 1: active generic user story already exists
    if (hasActiveGenericUS) {
      if (isGenericUS) {
        // cannot start a new voting while one is ongoing
        throw new ValidationException("error.userStory.newVoting.ongoing");
      }
      // update the existing generic user story
      return userStoryService.updateGenericUserStory(
        sprint.getId(), name, description, link
      );
    }

    // case 2: no generic user story exists yet
    if (isGenericUS && hasActiveUS) {
      // cannot start new voting if other user stories already exist
      throw new ForbiddenException();
    }

    if (isGenericUS) {
      userStoryService.closeRevealedUserStory(sprintId);
    }

    UserStoryResponse us = userStoryService.create(
      sprint.getId(), name, description, link, isGenericUS
    );

    sendUserStoryUpdatedEvent(sprintId);

    return us;
  }

  // ======== HELPERS ====================
  private Sprint getSprint(
    Long sprintId,
    Long userId,
    Set<SprintInclude> includes
  ) {
    participantService.ensureMember(userId, sprintId);

    return findSprintByInclude(sprintId, includes);
  }

  private Sprint getSprint(
    Long sprintId,
    Set<SprintInclude> includes
  ) {
    return findSprintByInclude(sprintId, includes);
  }

  private UserSprintsResponse getSprintsByMembership(Long memberId, Set<SprintMembership> memberships,    Set<SprintInclude> includes) {
    return this.findSprintsByMembership(memberId, memberships, includes);
  }

  private Sprint findSprintByInclude(
    Long sprintId,
    Set<SprintInclude> includes
  ) {
    boolean stories = includes.contains(SprintInclude.USER_STORIES);
    boolean estimationResults = includes.contains(SprintInclude.ESTIMATION_RESULTS);
    boolean estimations = includes.contains(SprintInclude.ESTIMATIONS);
    boolean members = includes.contains(SprintInclude.MEMBERS);

    Optional<Sprint> sprint = Optional.empty();
    int flags =
      (members ? 8 : 0) |
        (stories ? 4 : 0) |
        (estimations ? 2 : 0) |
        (estimationResults ? 1 : 0);

    sprint = switch (flags) {

      // STORIES + ESTIMATIONS + RESULTS
      case 15, 7 -> sprintRepository.findFull(sprintId);

      // STORIES + ESTIMATIONS
      case 14, 6 -> sprintRepository.findWithStoriesWithEstimations(sprintId);

      // STORIES + RESULTS
      case 13, 5 -> sprintRepository.findWithStoriesWithEstimationResults(sprintId);

      // STORIES only
      case 12, 4 -> sprintRepository.findWithStories(sprintId);

      // MEMBERS only
      case 8 -> sprintRepository.findWithMembers(sprintId);

      default -> sprintRepository.findById(sprintId);
    };


    return sprint.orElseThrow(EntityNotFoundException::new);
  }

  private UserSprintsResponse findSprintsByMembership(
    Long userId,
    Set<SprintMembership> memberships,
    Set<SprintInclude> includes
  ) {
    boolean all = memberships.contains(SprintMembership.ALL);
    boolean members = memberships.contains(SprintMembership.JOINED);
    boolean joinable = memberships.contains(SprintMembership.JOINABLE);

    int flags =
        (joinable ? 4 : 0) |
        (members ? 2 : 0) |
        (all ? 1 : 0);

    return switch (flags) {

      // JOINABLE only
      case 4 -> new UserSprintsResponse(
        List.of(),
        sprintResponseMapper.toResponseList(
          sprintRepository.findAllJoinableForUser(userId),
          includes
        )
      );

      // JOINED only
      case 2 -> new UserSprintsResponse(
        sprintResponseMapper.toResponseList(
          sprintRepository.findAllByMemberId(userId),
          includes
        ),
        List.of()
      );

      // ALL (joined + joinable)
      default -> {
        List<Sprint> joinedSprints = sprintRepository.findAllByMemberId(userId);
        List<Sprint> joinableSprints = sprintRepository.findAllJoinableForUser(userId);

        yield new UserSprintsResponse(
          sprintResponseMapper.toResponseList(joinedSprints, includes),
          sprintResponseMapper.toResponseList(joinableSprints, includes)
        );
      }
    };
  }


  private void validateSprintName(Sprint sprint) {
    if (sprintRepository.existsByName(sprint.getName())) {
      throw new ValidationException("error.name.exist");
    }
  }

  private void validateMemberAccess(UserResponse member, Sprint sprint) {
    // member should be invited by the creator of this sprint
    if (member.getInviter() == null ||
      !sprint.getCreator().getId().equals(member.getInviter().getId())) {
      throw new ForbiddenException();
    }
  }

  private void validateAdminAccess(UserResponse admin, Sprint sprint) {
    // admin should be the creator of sprint to join
    if (!sprint.getCreator().getId().equals(admin.getId())) {
      throw new ForbiddenException();
    }
  }

  private SprintResponse createSprintMember(Long sprintId, Long memberId) {
    participantService.createParticipant(sprintId, memberId);

    // get a refreshed entity to get the new member in the response
    return sprintResponseMapper.toResponse(sprintRepository.findById(sprintId).orElseThrow(), Set.of(SprintInclude.MEMBERS));
  }


  public void sendUserStoryUpdatedEvent(Long id) {
    try {
      System.out.println("Sending sprint updated event");

      Sprint sprint = sprintRepository.findFull(id)
        .orElseThrow(EntityNotFoundException::new);

      userStoryEventPublisher.publishSprintUpdated(sprintResponseMapper.toResponse(sprint, Set.of(SprintInclude.ESTIMATIONS,  SprintInclude.MEMBERS, SprintInclude.USER_STORIES, SprintInclude.ESTIMATION_RESULTS)));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
