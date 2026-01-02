package com.api.planning.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimateUserStoryRequest {
  @NotNull(message = "{error.estimation.required}")
  @Positive(message = "{error.estimation.positive}")
  private Double estimation;
}


