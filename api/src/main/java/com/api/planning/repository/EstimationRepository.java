package com.api.planning.repository;

import com.api.planning.entity.Estimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
  void deleteByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
  void deleteByUser_IdAndUserStory_IdAndIsOver(Long userId, Long userStoryId, boolean isOver);

  boolean existsByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
  boolean existsByUser_IdAndUserStory_IdAndIsOver(
    Long userId,
    Long userStoryId,
    boolean isOver
  );

  Optional<Estimation> findByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
  Optional<Estimation> findByUser_IdAndUserStory_IdAndIsOver(Long userId, Long userStoryId, boolean isOver);
  List<Estimation> findByUserStory_Id(Long userStoryId);

  @Modifying
  @Query("""
        UPDATE Estimation e
        SET e.isOver = true
        WHERE e.userStory.id = :userStoryId
    """)
  void markAllAsOver(@Param("userStoryId") Long userStoryId);
}
