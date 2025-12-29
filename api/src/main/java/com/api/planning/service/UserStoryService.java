package com.api.planning.service;

import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ValidationException;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.dto.response.UserStoryResponseWrapper;
import com.api.planning.entity.Sprint;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.UserStoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserStoryService {

  // don't use sprint service here
  private final UserStoryRepository userStoryRepository;
  private final UserStoryResponseWrapper userStoryResponseWrapper;
  private final EstimationService estimationService;
  private final CardDeckService cardDeckService;
  private final ParticipantService participantService;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Creates a user story for a sprint.
   * Notes:
   * - {@code name}, {@code description}, and {@code link} may be {@code null} for a generic user story.
   * - A generic user story is used only for voting and contains no descriptive information.
   * - Generic user stories is created automatically when starting a new voting
   *   or when all sprint user stories are deleted.
   */
  public UserStoryResponse createUserStory(
    Long sprintId,
    String name,
    String description,
    String link
  ) {
    UserStory userStory = UserStory.builder()
      .sprint(entityManager.getReference(Sprint.class, sprintId))
      .name(name)
      .description(description)
      .link(link)
      .build();

    userStoryRepository.save(userStory);

    return userStoryResponseWrapper.toResponse(userStory);
  }


  public UserStoryResponse updateGenericUserStory(Long sprintId, String name, String description, String link) {
    // find the active (not revealed) user story for the sprint
    UserStory userStory = userStoryRepository
      .findBySprint_IdAndNameAndIsVotingOver(sprintId, null, false).orElseThrow(() -> new ValidationException("error.userStory.notFound"));

    return updateUserStoryFields(userStory, name, description, link);
  }

  public SuccessResponse vote(Long userStoryId, Long userId, Integer estimation) {
    Sprint sprint = validateUserStoryAccess(userStoryId, userId).sprint();

    // check the estimation is a valid value
    if (!cardDeckService.has(sprint.getCardDeck(), estimation))
      throw new ValidationException("error.estimation.invalid");

    return estimationService.createOrUpdateEstimation(userStoryId, userId, estimation);
  }

  public SuccessResponse unVote(Long userStoryId, Long userId) {
    validateUserStoryAccess(userStoryId, userId);

    return estimationService.deleteEstimation(userStoryId, userId);
  }

  public SuccessResponse reveal(Long userStoryId, Long userId) {
    UserStory userStory = validateUserStoryAccess(userStoryId, userId).userStory();

    double estimation = estimationService.reveal(userStoryId);
    userStory.setIsVotingOver(true);
    userStory.setEstimation(estimation);
    userStoryRepository.save(userStory);

    return new SuccessResponse("");
  }

  // ======== HELPERS ====================
  private UserStoryAndSprint validateUserStoryAccess(Long userStoryId, Long userId) {
    UserStory userStory = userStoryRepository.findById(userStoryId)
      .orElseThrow(EntityNotFoundException::new);

    if (userStory.getIsVotingOver()) {
      throw new ValidationException("error.userStory.already_revealed");
    }

    Sprint sprint = userStory.getSprint();
    participantService.ensureMember(userId, sprint.getId());

    return new UserStoryAndSprint(userStory, sprint);
  }

  public record UserStoryAndSprint(UserStory userStory, Sprint sprint) {
  }

  public boolean hasActiveGenericUserStory(Long sprintId) {
    return userStoryRepository.existsBySprint_IdAndNameAndIsVotingOver(sprintId, null, false);
  }

  public boolean hasActiveUserStory(Long sprintId) {
    return userStoryRepository.existsBySprint_IdAndIsVotingOver(sprintId, false);
  }

  public UserStoryResponse updateUserStoryFields(UserStory userStory, String name, String description, String link) {
    userStory.setName(name);
    userStory.setDescription(description);
    userStory.setLink(link);

    userStoryRepository.save(userStory);

    return userStoryResponseWrapper.toResponse(userStory);
  }

}
