package com.api.planning.dto.response.estimation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstimationResultResponse {
  private Long id;
  private double total;
  private double estimation;
  private int count;
}
