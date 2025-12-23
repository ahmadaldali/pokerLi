package com.api.planning.service;

import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.planning.dto.response.SprintResponse;
import com.api.planning.dto.response.SprintResponseWrapper;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.dto.response.UserStoryResponseWrapper;
import com.api.planning.entity.Participant;
import com.api.planning.entity.Sprint;
import com.api.planning.repository.ParticipantRepository;
import com.api.planning.repository.SprintRepository;
import com.api.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SprintService {

  private final SprintRepository sprintRepository;
  private final ParticipantRepository participantRepository;
  private final SprintResponseWrapper sprintResponseWrapper;
  private final UserStoryService  userStoryService;


  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse createSprint(String name, String cardDeck, Long userId) {

    Sprint sprint = Sprint.builder()
      .name(name)
      .cardDeck(cardDeck)
      .creator(entityManager.getReference(User.class, userId))
      .build();

    sprintRepository.save(sprint);

    // auto-join creator
    joinInternal(sprint.getId(), userId);

    return new SuccessResponse("success");
  }

  public SprintResponse getSprint(Long sprintId, Long userId) {

    ensureMember(userId, sprintId);

    Sprint sprint = sprintRepository.findById(sprintId)
      .orElseThrow(EntityNotFoundException::new);

    return sprintResponseWrapper.toResponse(sprint);
  }


  public SuccessResponse join(Long sprintId, Long memberId) {
    sprintRepository.findById(sprintId)
      .orElseThrow(EntityNotFoundException::new);

    if (isMember(memberId, sprintId)) {
      throw new ValidationException("error.join.already_member");
    }

    joinInternal(sprintId, memberId);

    return new SuccessResponse("success");
  }

  // start new voting - create a new user story for this sprint
  public UserStoryResponse startNewVoting(Long sprintId, Long userId) {
    getSprint(sprintId, userId); // make sure sprint is existing and the user is a member

    return userStoryService.createUserStory(sprintId);
  }

  /* ================= HELPERS ================= */

  private void joinInternal(Long sprintId, Long memberId) {

    Participant participant = Participant.builder()
      .member(entityManager.getReference(User.class, memberId))
      .sprint(entityManager.getReference(Sprint.class, sprintId))
      .joinedDate(LocalDateTime.now())
      .build();

    participantRepository.save(participant);
  }

  private void ensureMember(Long memberId, Long sprintId) {
    if (!this.isMember(memberId, sprintId)) {
      throw new ForbiddenException();
    }
  }

  private boolean isMember(Long memberId, Long sprintId) {
    return participantRepository.existsByMemberIdAndSprintId(memberId, sprintId);
  }

}
