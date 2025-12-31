package com.api.planning.dto.response.estimation;


import com.api.planning.entity.Estimation;
import com.api.user.dto.response.UserResponseWrapper;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public record EstimationResponseWrapper(
  UserResponseWrapper userResponseWrapper
) {
  public EstimationResponse toResponse(Estimation estimation) {
    return EstimationResponse.builder()
      .id(estimation.getId())
      .estimation(estimation.getEstimation())
      .date(estimation.getDate())
      .isOnGoing(estimation.getEstimationResult() == null)
      .user(userResponseWrapper.toResponse(estimation.getUser()))  // Static method call
      .build();
  }
}

