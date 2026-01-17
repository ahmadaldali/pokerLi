package com.api.planning.service;


import com.api.common.dto.EstimationStats;
import com.api.common.dto.SuccessResponse;
import com.api.common.enums.SprintInclude;
import com.api.common.exception.ValidationException;
import com.api.event.publisher.UserStoryEventPublisher;
import com.api.planning.dto.response.estimation.EstimationResultResponse;
import com.api.planning.dto.response.userstory.UserStoryResponse;
import com.api.planning.dto.response.userstory.UserStoryResponseMapper;
import com.api.planning.entity.Sprint;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.UserStoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class UserStoryService {

  // repo
  private final UserStoryRepository userStoryRepository;

  // services
  private final EstimationService estimationService;
  private final EstimationResultService estimationResultService;
  private final ParticipantService participantService;
  private final UserStoryEventPublisher userStoryEventPublisher;

  // response
  private final UserStoryResponseMapper userStoryResponseMapper;


  @PersistenceContext
  private EntityManager entityManager;

  // ======== US CRUD ====================

  /**
   * Creates a user story for a sprint.
   * Notes:
   * - {@code name}, {@code description}, and {@code link} may be {@code null} for a generic user story.
   * - A generic user story is used only for voting and contains no descriptive information.
   * - Generic user stories is created automatically when starting a new voting
   * or when all sprint user stories are deleted.
   */
  public UserStoryResponse create(
    Long sprintId,
    String name,
    String description,
    String link,
    Boolean isActive
  ) {
    UserStory userStory = UserStory.builder()
      .sprint(entityManager.getReference(Sprint.class, sprintId))
      .name(name)
      .description(description)
      .link(link)
      .isActive(isActive)
      .build();

    userStoryRepository.save(userStory);

    return userStoryResponseMapper.toResponse(userStory);
  }

  public UserStoryResponse updateGenericUserStory(Long sprintId, String name, String description, String link) {
    // find the active (not revealed) user story for the sprint
    UserStory userStory = userStoryRepository
      .findBySprint_IdAndNameAndIsRevealed(sprintId, null, false).orElseThrow(() -> new ValidationException("error.userStory.notFound"));

    updateUserStoryFields(userStory, name, description, link);

    return userStoryResponseMapper.toResponse(userStory);
  }

  // when we start a new voting "create a new generic us"
  public void closeRevealedUserStory(Long sprintId) {
    // find the active and revealed user story for the sprint
    Optional<UserStory> userStoryOpt =
      userStoryRepository.findBySprint_IdAndIsActiveAndIsRevealed(
        sprintId, true, true
      );

    userStoryOpt.ifPresent(userStory -> {
      userStory.setIsActive(false);
      userStoryRepository.save(userStory);
    });
  }

  @Transactional
  public SuccessResponse delete(Long userStoryId, Long userId) {
    UserStory userStory = getUserStoryWithSprint(userStoryId, userId).userStory();

    // Cascades deletion for Estimations and EstimationResults
    userStoryRepository.deleteById(userStory.getId());

    return new SuccessResponse("");
  }

  // ======== US actions ====================
  public SuccessResponse vote(Long userStoryId, Long userId, Double estimation) {
    Sprint sprint = getActiveUserStoryWithSprint(userStoryId, userId).sprint();

    // check the estimation is a valid value
    if (!sprint.getSequence().contains(estimation))
      throw new ValidationException("error.estimation.invalid");

    SuccessResponse response = estimationService.createOrUpdateEstimation(userStoryId, userId, estimation);

    sendUserStoryUpdatedEvent(userStoryId);

    return response;
  }

  public SuccessResponse unVote(Long userStoryId, Long userId) {
    getActiveUserStoryWithSprint(userStoryId, userId);

    SuccessResponse response = estimationService.deleteEstimation(userStoryId, userId);

    sendUserStoryUpdatedEvent(userStoryId);

    return response;
  }

  @Transactional
  public SuccessResponse reveal(Long userStoryId, Long userId) {
    UserStory userStory = getActiveUserStoryWithSprint(userStoryId, userId).userStory();

    EstimationStats estimationStats = estimationService.getEstimationStatsForStory(userStoryId);

    // save the estimations result
    EstimationResultResponse estResultResponse = estimationResultService.createEstimation(userStoryId, estimationStats);

    // reveal estimations
    estimationService.reveal(userStoryId, estResultResponse.getId());

    // end voting for this US
    userStory.setIsRevealed(true);
    userStoryRepository.save(userStory);

    sendUserStoryUpdatedEvent(userStoryId);

    return new SuccessResponse("");
  }

  /**
   * vote again = start new voting again for a story
   * start new voting again = is_revealed = false
   * So story should be already voted , is_revealed = true
   */
  @Transactional
  public UserStoryResponse voteAgain(Long userStoryId, Long userId) {
    return activateStory(userStoryId, userId, true);
  }

  /**
   * Make the other active are not active (sure for this sprint)
   * Make the selected one is active
   */
  @Transactional
  public UserStoryResponse select(Long userStoryId, Long userId) {
    return activateStory(userStoryId, userId, false);
  }

  // ======== HELPERS ====================

  public UserStoryAndSprint getUserStoryWithSprint(Long userStoryId, Long userId) {
    UserStory userStory = userStoryRepository.findById(userStoryId)
      .orElseThrow(EntityNotFoundException::new);

    Sprint sprint = userStory.getSprint();
    participantService.ensureMember(userId, sprint.getId());

    return new UserStoryAndSprint(userStory, sprint);
  }

  public UserStoryAndSprint getActiveUserStoryWithSprint(Long userStoryId, Long userId) {
    UserStoryAndSprint userStoryWithSprint = this.getUserStoryWithSprint(userStoryId, userId);

    if (userStoryWithSprint.userStory().getIsRevealed()) {
      throw new ValidationException("error.userStory.already_revealed");
    }

    return userStoryWithSprint;
  }

  public UserStoryAndSprint getVotedUserStoryWithSprint(Long userStoryId, Long userId) {
    UserStoryAndSprint userStoryWithSprint = this.getUserStoryWithSprint(userStoryId, userId);

    if (!userStoryWithSprint.userStory().getIsRevealed()) {
      throw new ValidationException("error.userStory.not_revealed");
    }

    return userStoryWithSprint;
  }

  public boolean hasActiveGenericUserStory(Long sprintId) {
    return userStoryRepository.existsBySprint_IdAndNameAndIsRevealed(sprintId, null, false);
  }

  public boolean hasActiveUserStory(Long sprintId) {
    return userStoryRepository.existsBySprint_IdAndIsRevealed(sprintId, false);
  }

  public void updateUserStoryFields(UserStory userStory, String name, String description, String link) {
    userStory.setName(name);
    userStory.setDescription(description);
    userStory.setLink(link);

    userStoryRepository.save(userStory);
  }

  public UserStoryResponse activateStory(
    Long userStoryId,
    Long userId,
    boolean resetRevealState
  ) {
    UserStoryAndSprint result = getUserStoryWithSprint(userStoryId, userId);

    Sprint sprint = result.sprint();
    userStoryRepository.deactivateAllBySprintId(sprint.getId());

    UserStory story = result.userStory();

    if (resetRevealState) {
      story.setIsRevealed(false);
    }

    story.setIsActive(true);
    userStoryRepository.save(story);

    return userStoryResponseMapper.toResponse(story);
  }


  public void sendUserStoryUpdatedEvent(Long userStoryId) {
    try {
      System.out.println("Sending user story updated event");

      UserStory userStory = userStoryRepository.findFull(userStoryId)
        .orElseThrow(EntityNotFoundException::new);

      userStoryEventPublisher.publishUserStoryUpdated(
        userStoryResponseMapper.toResponse(
          userStory,
          Set.of(SprintInclude.ESTIMATIONS, SprintInclude.ESTIMATION_RESULTS)
        )
      );

    } catch (Exception e) {
      System.err.println("Error sending user story updated event for id=" + userStoryId);
      e.printStackTrace();
    }
  }

  public record UserStoryAndSprint(UserStory userStory, Sprint sprint) {
  }

}
