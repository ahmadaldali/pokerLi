package com.api.planning.dto.response.estimation;

import com.api.planning.entity.Estimation;
import com.api.user.dto.response.UserResponseMapper;
import org.springframework.stereotype.Component;

@Component
public record EstimationResponseMapper(
  UserResponseMapper userResponseMapper
) {

  public EstimationResponse toResponse(Estimation estimation) {
    return EstimationResponse.builder()
      .id(estimation.getId())
      .estimation(estimation.getEstimation())
      .estimationResultId(safeGetEstimationResultId(estimation))
      .date(estimation.getDate())
      .user(userResponseMapper.toResponse(estimation.getUser()))
      .build();
  }

  private Long safeGetEstimationResultId(Estimation estimation) {
    if (estimation.getEstimationResult() == null) {
      return null;
    }

    return estimation.getEstimationResult().getId();
  }
}
