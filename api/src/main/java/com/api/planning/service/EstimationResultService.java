package com.api.planning.service;


import com.api.common.dto.EstimationStats;
import com.api.planning.dto.response.estimation.EstimationResultResponse;
import com.api.planning.dto.response.estimation.EstimationResultResponseMapper;
import com.api.planning.entity.EstimationResult;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.EstimationResultRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimationResultService {

  private final EstimationResultRepository estimationResultRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public EstimationResultResponse createEstimation(Long userStoryId, EstimationStats estimationStats) {
    EstimationResult entity = EstimationResult.builder().userStory(entityManager.getReference(UserStory.class, userStoryId))
      .estimation(estimationStats.average())
      .count(estimationStats.count())
      .total(estimationStats.total())
      .date(LocalDateTime.now())
      .build();

    estimationResultRepository.save(entity);

    return new EstimationResultResponseMapper().toResponse(entity);
  }

}
