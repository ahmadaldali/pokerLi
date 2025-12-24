package com.api.planning.service;


import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.dto.response.UserStoryResponseWrapper;
import com.api.planning.entity.Sprint;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.UserStoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
@Transactional
public class UserStoryService {

  private final UserStoryRepository userStoryRepository;
  private final EstimationService estimationService;
  private final UserStoryResponseWrapper userStoryResponseWrapper;
  private final CardDeckService cardDeckService;
  private final ParticipantService participantService;

  @PersistenceContext
  private EntityManager entityManager;

  public UserStoryResponse createUserStory(Long sprintId) {

    UserStory entity = UserStory.builder().sprint(entityManager.getReference(Sprint.class, sprintId)).isVotingOver(false) // voting ongoing for this user-story
      .build();

    userStoryRepository.save(entity);

    return userStoryResponseWrapper.toResponse(entity);
  }


  public SuccessResponse vote(Long userStoryId, Long userId, Integer estimation) {

    UserStory userStory = userStoryRepository.findById(userStoryId).orElseThrow(() -> new EntityNotFoundException(""));

    Sprint sprint = userStory.getSprint();

    if (!participantService.isMember(userId, sprint.getId())) {
      throw new ForbiddenException();
    }

    // check the estimation is a valid value
    if (!cardDeckService.has(sprint.getCardDeck(), estimation)) throw new ValidationException("error.estimation.invalid");

    return estimationService.createEstimation(userStoryId, userId, estimation);
  }

  public SuccessResponse unVote(Long userStoryId, Long userId) {
    userStoryRepository.findById(userStoryId).orElseThrow(() -> new EntityNotFoundException(""));

    return estimationService.deleteEstimation(userStoryId, userId);
  }

}
