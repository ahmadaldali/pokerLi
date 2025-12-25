package com.api.planning.repository;

import com.api.planning.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
  boolean existsByMemberIdAndSprintId(Long memberId, Long sprintId);
}
