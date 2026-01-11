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
  private List<EstimationResultResponse> estimationResults;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EstimationResponse> estimations;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Long> voters;
}
