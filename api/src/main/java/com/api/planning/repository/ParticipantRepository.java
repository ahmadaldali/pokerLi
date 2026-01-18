package com.api.planning.repository;

import com.api.planning.entity.Participant;
import com.api.planning.entity.Sprint;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
  boolean existsByMemberIdAndSprintId(Long memberId, Long sprintId);
}
