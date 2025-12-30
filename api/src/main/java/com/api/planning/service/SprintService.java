package com.api.planning.service;

import com.api.common.dto.SuccessResponse;
import com.api.common.enums.UserRole;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.planning.dto.response.sprint.SprintResponseWithUserStories;
import com.api.planning.dto.response.sprint.SprintResponseWithUserStoriesWrapper;
import com.api.planning.dto.response.sprint.SprintResponseWrapper;
import com.api.planning.dto.response.userstory.UserStoryResponse;
import com.api.planning.entity.Sprint;
import com.api.planning.repository.SprintRepository;
import com.api.user.entity.User;
import com.api.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class SprintService {
  // repo
  private final SprintRepository sprintRepository;

  // services
  private final UserStoryService userStoryService;
  private final ParticipantService participantService;
  private final UserService  userService;

  // response
  private final SprintResponseWrapper sprintResponseWrapper;
  private final SprintResponseWithUserStoriesWrapper sprintResponseWithUserStoriesWrapper;


  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse create(String name, JsonNode cardDeck, Long userId) {
    userService.ensureAdmin(userId);

    Sprint sprint = Sprint.builder().name(name).cardDeck(cardDeck).creator(entityManager.getReference(User.class, userId)).build();

    if (sprintRepository.existsByName(name)) {
      throw new ValidationException("error.name.exist");
    }

    sprintRepository.save(sprint);

    // auto-join creator
    participantService.createParticipant(sprint.getId(), userId);

    return new SuccessResponse("success");
  }

  public SprintResponseWithUserStories get(Long sprintId, Long userId) {
    Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    participantService.ensureMember(userId, sprintId);

    return sprintResponseWithUserStoriesWrapper.toResponse(sprint);
  }


  public SuccessResponse join(Long sprintId, Long memberId) {
    Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    if (participantService.isMember(memberId, sprintId)) {
      throw new ValidationException("error.sprint.already_member");
    }

    User member = userService.getUser(memberId);
    if (member.getRole() == UserRole.MEMBER) {
      if (!sprint.getCreator().getId().equals(member.getInviter().getId())) {
        throw new ForbiddenException(); // members can join rooms only were created by the inviter
      }
    } else {
      if (member.getRole() == UserRole.ADMIN) {
        if (!Objects.equals(member.getId(), sprint.getCreator().getId())) {
          throw new ForbiddenException(); // admin can join only their rooms
        }
      }
      // guest can join any room.
    }

    participantService.createParticipant(sprintId, memberId);

    return new SuccessResponse("success");
  }

  // sprint user-stories
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
    Sprint sprint = getSprint(sprintId, userId);

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

    return userStoryService.create(
      sprint.getId(), name, description, link
    );
  }

  // get the entity
  public Sprint getSprint(Long sprintId, Long userId) {
    Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    participantService.ensureMember(userId, sprintId);

    return sprint;
  }

}
