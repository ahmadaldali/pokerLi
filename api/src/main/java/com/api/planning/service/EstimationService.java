package com.api.planning.service;


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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimationService {

  private final EstimationRepository estimationRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse createEstimation(Long userStoryId, Long userId, Integer estimation) {
    Estimation entity = Estimation.builder().user(entityManager.getReference(User.class, userId))
      .userStory(entityManager.getReference(UserStory.class, userStoryId))
      .estimation(estimation)
      .date(LocalDateTime.now())
      .build();

    estimationRepository.save(entity);

    return new SuccessResponse("");
  }

  public SuccessResponse updateEstimation(Long userStoryId, Long userId, Integer estimation) {
    // get the ongoing estimation only to update it (don't get from the history)
    Estimation entity = estimationRepository.findByUser_IdAndUserStory_IdAndIsOver(userId, userStoryId, false)
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
    estimationRepository.deleteByUser_IdAndUserStory_IdAndIsOver(userId, userStoryId, false);

    return new SuccessResponse("");
  }

  // ======== HELPERS ====================
  public boolean userHasOngoingEstimation(Long userStoryId, Long userId) {
    // check only the ongoing estimations - don't look at everything (history)
    return estimationRepository.existsByUser_IdAndUserStory_IdAndIsOver(userStoryId, userId, false);
  }

  public SuccessResponse createOrUpdateEstimation(Long userStoryId, Long userId, Integer estimation) {
    if (this.userHasOngoingEstimation(userId, userStoryId)) {
      return this.updateEstimation(userStoryId, userId, estimation);
    } else {
      return this.createEstimation(userStoryId, userId, estimation);
    }
  }

  @Transactional
  public double reveal(Long userStoryId) {
    List<Estimation> estimations = estimationRepository.findByUserStory_Id(userStoryId);

    if (estimations.isEmpty()) {
      return 0.0;
    }

    estimationRepository.markAllAsOver(userStoryId);

    double average = estimations.stream()
      .mapToDouble(Estimation::getEstimation)
      .average()
      .orElse(0.0);

    return Math.round(average * 100.0) / 100.0;
  }

}
