package com.api.planning.dto.response.estimation;


import com.api.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EstimationResponse {
  private Long id;
  private double estimation;
  private LocalDateTime date;
  private boolean isOnGoing;
  private UserResponse user;
}
