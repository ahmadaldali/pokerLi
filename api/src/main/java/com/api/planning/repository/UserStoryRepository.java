package com.api.planning.repository;

import com.api.planning.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

  boolean existsBySprint_IdAndIsVotingOver(
    Long sprintId,
    boolean isVotingOver
  );

  boolean existsBySprint_IdAndNameAndIsVotingOver(
    Long sprintId,
    String name,
    boolean isVotingOver
  );

  Optional<UserStory> findBySprint_IdAndNameAndIsVotingOver(
    Long sprintId,
    String name,
    boolean isVotingOver
  );
}
