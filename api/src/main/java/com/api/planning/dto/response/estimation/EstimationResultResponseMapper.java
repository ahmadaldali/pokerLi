package com.api.planning.dto.response.estimation;


import com.api.planning.entity.EstimationResult;
import org.springframework.stereotype.Service;

@Service
public record EstimationResultResponseMapper() {
  public EstimationResultResponse toResponse(EstimationResult estimationResult) {
    return new EstimationResultResponse(
      estimationResult.getId(),
      estimationResult.getTotal(),
      estimationResult.getEstimation(),
      estimationResult.getCount()
    );
  }
}
