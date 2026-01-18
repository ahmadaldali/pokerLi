package com.api.planning.repository;

import com.api.planning.entity.Estimation;
import com.api.planning.entity.EstimationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {

  void deleteByUser_IdAndUserStory_IdAndEstimationResult_Id(
    Long userId,
    Long userStoryId,
    Long estimationResultId);

  boolean existsByUser_IdAndUserStory_IdAndEstimationResult_Id(
    Long userId,
    Long userStoryId,
    Long estimationResultId
  );

  Optional<Estimation> findByUser_IdAndUserStory_IdAndEstimationResult_Id(Long userId, Long userStoryId, Long estimationResultId);
  List<Estimation> findByUserStory_IdAndEstimationResult_Id(Long userStoryId,  Long estimationResultId);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query(
    value = """
      UPDATE estimations
      SET estimation_result_id = :estimationResultId
      WHERE user_story_id = :userStoryId
        AND estimation_result_id IS NULL
  """,
    nativeQuery = true
  )
  void attachResultToOngoingEstimations(
    @Param("userStoryId") Long userStoryId,
    @Param("estimationResultId") Long estimationResultId
  );
}
