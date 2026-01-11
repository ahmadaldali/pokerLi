package com.api.planning.dto.response.estimation;


import com.api.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstimationResponse {
  private Long id;
  private double estimation;
  private LocalDateTime date;
  private UserResponse user;
  private Long estimationResultId;
}
