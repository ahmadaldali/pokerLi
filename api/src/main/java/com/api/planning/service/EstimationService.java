package com.api.planning.service;


import com.api.common.dto.EstimationStats;
import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.planning.entity.Estimation;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.EstimationRepository;
import com.api.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimationService {

  private final EstimationRepository estimationRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse createEstimation(Long userStoryId, Long userId, Double estimation) {
    Estimation entity = Estimation.builder().user(entityManager.getReference(User.class, userId))
      .userStory(entityManager.getReference(UserStory.class, userStoryId))
      .estimation(estimation)
      .date(LocalDateTime.now())
      .build();

    estimationRepository.save(entity);

    return new SuccessResponse("");
  }

  public SuccessResponse updateEstimation(Long userStoryId, Long userId, Double estimation) {
    // get the ongoing estimation (estimationResult is null) only to update it
    Estimation entity = estimationRepository.findByUser_IdAndUserStory_IdAndEstimationResult_Id(userId, userStoryId, null)
      .orElseThrow(() -> new ValidationException("error.estimation.notFound"));

    entity.setEstimation(estimation);
    entity.setDate(LocalDateTime.now());

    estimationRepository.save(entity);

    return new SuccessResponse("");
  }

  public SuccessResponse deleteEstimation(Long userStoryId, Long userId) {
    if (!this.userHasOngoingEstimation(userId, userStoryId)) {
      throw new ForbiddenException();
    }

    // un-vote the ongoing estimation
    estimationRepository.deleteByUser_IdAndUserStory_IdAndEstimationResult_Id(userId, userStoryId, null);

    return new SuccessResponse("");
  }

  // ======== HELPERS ====================
  public boolean userHasOngoingEstimation(Long userStoryId, Long userId) {
    // check only the ongoing estimations (estimationResultId is NULL)
    return estimationRepository.existsByUser_IdAndUserStory_IdAndEstimationResult_Id(userStoryId, userId, null);
  }

  public SuccessResponse createOrUpdateEstimation(Long userStoryId, Long userId, Double estimation) {
    if (this.userHasOngoingEstimation(userId, userStoryId)) {
      return this.updateEstimation(userStoryId, userId, estimation);
    } else {
      return this.createEstimation(userStoryId, userId, estimation);
    }
  }

  public EstimationStats getEstimationStatsForStory(Long userStoryId) {

    List<Estimation> estimations =
      estimationRepository.findByUserStory_IdAndEstimationResult_Id(userStoryId, null);

    if (estimations.isEmpty()) {
      return new EstimationStats(0.0, 0.0, 0);
    }

    DoubleSummaryStatistics stats = estimations.stream()
      .mapToDouble(Estimation::getEstimation)
      .summaryStatistics();

    double total = stats.getSum();
    int count = (int) stats.getCount();
    double average = Math.round(stats.getAverage() * 100.0) / 100.0;

    return new EstimationStats(total, average, count);
  }

  public void reveal(Long userStoryId, Long estimationResultId) {
    estimationRepository.attachResultToOngoingEstimations(userStoryId, estimationResultId);
  }

}
