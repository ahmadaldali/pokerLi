package com.api.planning.dto.response.userstory;


import com.api.common.enums.SprintInclude;
import com.api.planning.dto.response.estimation.EstimationResponseMapper;
import com.api.planning.dto.response.estimation.EstimationResultResponseMapper;
import com.api.planning.entity.UserStory;
import org.springframework.stereotype.Service;

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
    Set<SprintInclude> safeIncludes =
      includes == null ? NO_INCLUDES : includes;

    UserStoryResponse response = baseResponse(userStory);

    if (safeIncludes.contains(SprintInclude.ESTIMATION_RESULTS)) {
      response.setEstimationResults(
        userStory.getEstimationResults().stream()
          .map(estimationResultResponseMapper::toResponse)
          .toList()
      );
    }

    if (safeIncludes.contains(SprintInclude.ESTIMATIONS)) {
      var estimations = userStory.getEstimations();

      response.setEstimations(
        estimations.stream()
          .map(estimationResponseMapper::toResponse)
          .toList()
      );

      response.setVoters(
        estimations.stream()
          .map(estimation -> estimation.getUser().getId())
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
      .isVotingOver(userStory.getIsVotingOver())
      .build();
  }
}
