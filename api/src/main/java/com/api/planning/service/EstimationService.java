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

@Service
@RequiredArgsConstructor
@Transactional
public class EstimationService {

  private final EstimationRepository estimationRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public SuccessResponse createEstimation(Long userStoryId, Long userId, Integer estimation) {
    Estimation entity = Estimation.builder().user(entityManager.getReference(User.class, userId)).userStory(entityManager.getReference(UserStory.class, userStoryId)).estimation(estimation).date(LocalDateTime.now()).build();

    estimationRepository.save(entity);

    return new SuccessResponse("");
  }

  public SuccessResponse updateEstimation(Long userStoryId, Long userId, Integer estimation) {
    Estimation entity = estimationRepository.findByUser_IdAndUserStory_Id(userId, userStoryId)
      .orElseThrow(() -> new ValidationException("error.estimation.notFound"));

    entity.setEstimation(estimation);
    entity.setDate(LocalDateTime.now());

    estimationRepository.save(entity);

    return new SuccessResponse("");
  }

  public SuccessResponse deleteEstimation(Long userStoryId, Long userId) {
    if (!this.userHasEstimation(userId, userStoryId)) {
      throw new ForbiddenException();
    }

    estimationRepository.deleteByUser_IdAndUserStory_Id(userId, userStoryId);

    return new SuccessResponse("");
  }

  // ======== HELPERS ====================

  public boolean userHasEstimation(Long userStoryId, Long userId) {
    return estimationRepository.existsByUser_IdAndUserStory_Id(userStoryId, userId);
  }

  public SuccessResponse createOrUpdateEstimation(Long userStoryId, Long userId, Integer estimation) {
    if (this.userHasEstimation(userId, userStoryId)) {
      return this.updateEstimation(userStoryId, userId, estimation);
    } else {
      return this.createEstimation(userStoryId, userId, estimation);
    }
  }

}
