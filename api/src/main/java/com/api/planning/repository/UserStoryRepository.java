package com.api.planning.repository;

import com.api.planning.entity.UserStory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

  @EntityGraph(attributePaths = {
    "estimations",
    "estimationResults"
  })
  @Query("select us from UserStory us where us.id = :id")
  Optional<UserStory> findFull(Long id);

  @EntityGraph(attributePaths = {
    "estimations",
    "estimations.user"
  })
  @Query("SELECT us FROM UserStory us LEFT JOIN FETCH us.estimations e " +
    "WHERE us.id = :userStoryId AND (e.estimationResult IS NULL)")
  Optional<UserStory> findWithActiveEstimations(Long userStoryId);
}
