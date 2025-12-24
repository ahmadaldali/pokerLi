package com.api.planning.service;

import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ValidationException;
import com.api.planning.dto.response.SprintResponse;
import com.api.planning.dto.response.SprintResponseWrapper;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.entity.Sprint;
import com.api.planning.repository.SprintRepository;
import com.api.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class SprintService {

  private final SprintRepository sprintRepository;
  private final SprintResponseWrapper sprintResponseWrapper;
  private final UserStoryService userStoryService;
  private final ParticipantService participantService;


  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse createSprint(String name, String cardDeck, Long userId) {

    Sprint sprint = Sprint.builder().name(name).cardDeck(cardDeck).creator(entityManager.getReference(User.class, userId)).build();

    sprintRepository.save(sprint);

    // auto-join creator
    participantService.createParticipant(sprint.getId(), userId);

    return new SuccessResponse("success");
  }

  public SprintResponse getSprint(Long sprintId, Long userId) {

    participantService.ensureMember(userId, sprintId);

    Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    return sprintResponseWrapper.toResponse(sprint);
  }


  public SuccessResponse join(Long sprintId, Long memberId) {
    sprintRepository.findById(sprintId).orElseThrow(EntityNotFoundException::new);

    if (participantService.isMember(memberId, sprintId)) {
      throw new ValidationException("error.join.already_member");
    }

    participantService.createParticipant(sprintId, memberId);

    return new SuccessResponse("success");
  }

  // start new voting - create a new user story for this sprint
  public UserStoryResponse startNewVoting(Long sprintId, Long userId) {
    getSprint(sprintId, userId); // make sure sprint is existing and the user is a member

    return userStoryService.createUserStory(sprintId);
  }

}
