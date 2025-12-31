package com.api.planning.service;

import com.api.common.exception.ForbiddenException;
import com.api.planning.entity.Participant;
import com.api.planning.entity.Sprint;
import com.api.planning.repository.ParticipantRepository;
import com.api.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {

  private final ParticipantRepository participantRepository;

  @PersistenceContext
  private EntityManager entityManager;

  // TODO: change the type of the returned value
  public void createParticipant(Long sprintId, Long memberId) {
    Participant participant = Participant.builder()
      .member(entityManager.getReference(User.class, memberId))
      .sprint(entityManager.getReference(Sprint.class, sprintId))
      .joinedDate(LocalDateTime.now())
      .build();

    participantRepository.save(participant);
  }

  public void ensureMember(Long memberId, Long sprintId) {
    if (!this.isMember(memberId, sprintId)) {
      throw new ForbiddenException();
    }
  }

  public boolean isMember(Long memberId, Long sprintId) {
    return participantRepository.existsByMemberIdAndSprintId(memberId, sprintId);
  }
}
