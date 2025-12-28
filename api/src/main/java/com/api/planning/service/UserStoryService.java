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

  public UserStoryResponse createUserStory(Long sprintId, String name, String description, String link) {
    // name, desc, and link could be null for the generic user story
    // generic story is a story just for voting. (no info)
    // can be used for the start new voting feature - or when you delete all sprint's stories, a new generic will be created auto
    UserStory entity = UserStory.builder().sprint(entityManager.getReference(Sprint.class, sprintId))
      .name(name)
      .description(description)
      .link(link)
      .build();

    userStoryRepository.save(entity);

    return userStoryResponseWrapper.toResponse(entity);
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

}
