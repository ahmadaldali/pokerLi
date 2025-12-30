package com.api.planning.dto.response.userstory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryResponse {
  private Long id;
  private String name;
  private String description;
  private String link;
}
