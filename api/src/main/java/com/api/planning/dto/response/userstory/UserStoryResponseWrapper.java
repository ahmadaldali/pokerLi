package com.api.planning.dto.response.userstory;

import com.api.common.enums.SprintInclude;
import com.api.planning.dto.response.estimation.EstimationResponseWrapper;
import com.api.planning.dto.response.estimation.EstimationResultResponseWrapper;
import com.api.planning.entity.UserStory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public record UserStoryResponseWrapper(
  EstimationResultResponseWrapper estimationResultResponseWrapper,
  EstimationResponseWrapper estimationResponseWrapper
) {

  private static final Set<SprintInclude> NO_INCLUDES = Set.of();

  public UserStoryResponse toResponse(UserStory userStory) {
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
        userStory.getEstimationResults()
          .stream()
          .map(estimationResultResponseWrapper::toResponse)
          .toList()
      );
    }

    if (safeIncludes.contains(SprintInclude.ESTIMATIONS)) {
      response.setEstimations(
        userStory.getEstimations()
          .stream()
          .map(estimationResponseWrapper::toResponse)
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
