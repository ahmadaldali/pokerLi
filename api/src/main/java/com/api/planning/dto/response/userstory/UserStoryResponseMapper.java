package com.api.planning.dto.response.userstory;

import com.api.common.enums.SprintInclude;
import com.api.planning.dto.response.estimation.EstimationResponseMapper;
import com.api.planning.dto.response.estimation.EstimationResultResponseMapper;
import com.api.planning.entity.UserStory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public record UserStoryResponseMapper(
  EstimationResultResponseMapper estimationResultResponseMapper,
  EstimationResponseMapper estimationResponseMapper
) {

  private static final Set<SprintInclude> NO_INCLUDES = Set.of();

  public UserStoryResponse toResponse(UserStory userStory) {
    if (userStory == null) {
      return null;
    }
    return toResponse(userStory, NO_INCLUDES);
  }

  public UserStoryResponse toResponse(
    UserStory userStory,
    Set<SprintInclude> includes
  ) {
    if (userStory == null) {
      return null;
    }

    Set<SprintInclude> safeIncludes =
      includes == null ? NO_INCLUDES : includes;

    UserStoryResponse response = baseResponse(userStory);

    if (safeIncludes.contains(SprintInclude.ESTIMATION_RESULTS)) {
      response.setEstimationResults(
        Optional.ofNullable(userStory.getEstimationResults())
          .orElse(Collections.emptySet())
          .stream()
          .map(estimationResultResponseMapper::toResponse)
          .toList()
      );
    }

    if (safeIncludes.contains(SprintInclude.ESTIMATIONS)) {
      var estimations =
        Optional.ofNullable(userStory.getEstimations())
          .orElse(Collections.emptySet());

      response.setEstimations(
        estimations.stream()
          .map(estimationResponseMapper::toResponse)
          .toList()
      );

      Long lastEstimationResultId = null;

      if (userStory.getIsRevealed()) {
        lastEstimationResultId =
          Optional.ofNullable(userStory.getEstimationResults())
            .orElse(Collections.emptySet())
            .stream()
            .map(er -> er.getId())
            .max(Long::compareTo) // last = highest id
            .orElse(null);
      }

      final Long finalLastEstimationResultId = lastEstimationResultId;

      response.setVoters(
        estimations.stream()
          .filter(est ->
            userStory.getIsRevealed()
              ? est.getEstimationResult() != null
              && est.getEstimationResult().getId().equals(finalLastEstimationResultId)
              : est.getEstimationResult() == null
          )
          .map(est -> est.getUser().getId())
          .distinct()
          .toList()
      );
    }

    return response;
  }

  private UserStoryResponse baseResponse(UserStory userStory) {
    return UserStoryResponse.builder()
      .id(userStory.getId())
      .name(userStory.getName())
      .description(userStory.getDescription())
      .link(userStory.getLink())
      .isRevealed(userStory.getIsRevealed())
      .isActive(userStory.getIsActive())
      .build();
  }
}
