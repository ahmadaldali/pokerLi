package com.api.planning.repository;

import com.api.planning.entity.UserStory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

  boolean existsBySprint_IdAndIsRevealed(
    Long sprintId,
    boolean isRevealed
  );

  boolean existsBySprint_IdAndNameAndIsRevealed(
    Long sprintId,
    String name,
    boolean isRevealed
  );

  Optional<UserStory> findBySprint_IdAndNameAndIsRevealed(
    Long sprintId,
    String name,
    boolean isRevealed
  );

  Optional<UserStory> findBySprint_IdAndIsActiveAndIsRevealed(
    Long sprintId,
    boolean isActive,
    boolean isRevealed
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

  @Modifying(clearAutomatically = true)
  @Query("UPDATE UserStory us SET us.isActive = false WHERE us.sprint.id = :sprintId")
  int deactivateAllBySprintId(@Param("sprintId") Long sprintId);
}
