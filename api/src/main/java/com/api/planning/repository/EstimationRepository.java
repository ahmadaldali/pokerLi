package com.api.planning.repository;

import com.api.planning.entity.Estimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
  void deleteByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
  boolean existsByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
  Optional<Estimation> findByUser_IdAndUserStory_Id(Long userId, Long userStoryId);
}
