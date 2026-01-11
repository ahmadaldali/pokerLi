package com.api.planning.dto.response.userstory;


import com.api.planning.dto.response.estimation.EstimationResponse;
import com.api.planning.dto.response.estimation.EstimationResultResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserStoryResponse {
  private Long id;
  private String name;
  private String description;
  private String link;
  private Boolean isRevealed;
  private Boolean isActive; // voting ongoing for this us. we keep only one us active at the same moment

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EstimationResultResponse> estimationResults; // history results (after revealed)

  // TODO: get the last result only, we need the history for reports/dashboard not for voting

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EstimationResponse> estimations;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Long> voters; // active voters (voters for the ongoing voting or the last revealed)
}
