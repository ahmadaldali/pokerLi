package com.api.planning.repository;

import com.api.planning.entity.Sprint;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
  Boolean existsByName(String name);

  @EntityGraph(attributePaths = "creator")
  Optional<Sprint> findById(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStories(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimationResults"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStoriesWithEstimationResults(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimations"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStoriesWithEstimations(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimations",
    "userStories.estimationResults"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findFull(Long id);
}
