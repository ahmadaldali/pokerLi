package com.api.planning.service;


import com.api.common.dto.SuccessResponse;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.dto.response.UserStoryResponseWrapper;
import com.api.planning.entity.Estimation;
import com.api.planning.entity.Sprint;
import com.api.planning.entity.UserStory;
import com.api.planning.repository.EstimationRepository;
import com.api.planning.repository.UserStoryRepository;
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
public class UserStoryService {

  private final UserStoryRepository userStoryRepository;
  private final EstimationRepository estimationRepository;
  private final UserStoryResponseWrapper  userStoryResponseWrapper;

  @PersistenceContext
  private EntityManager entityManager;

  public UserStoryResponse createUserStory(Long sprintId) {

    UserStory entity = UserStory.builder()
      .sprint(entityManager.getReference(Sprint.class, sprintId))
      .isVotingOver(false) // voting ongoing for this user-story
      .build();

    userStoryRepository.save(entity);

    return userStoryResponseWrapper.toResponse(entity);
  }


  public SuccessResponse vote(Long userStoryId, Long userId, Integer estimation) {

    Estimation entity = Estimation.builder()
      .user(entityManager.getReference(User.class, userId))
      .userStory(entityManager.getReference(UserStory.class, userStoryId))
      .estimation(estimation)
      .date(LocalDateTime.now())
      .build();

    estimationRepository.save(entity);

    return new SuccessResponse("s");
  }

  /* ================= HELPERS ================= */



}
