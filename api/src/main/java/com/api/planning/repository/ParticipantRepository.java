package com.api.planning.repository;

import com.api.planning.entity.Participant;
import com.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
  boolean existsByMemberIdAndSprintId(Long memberId, Long sprintId);
}
