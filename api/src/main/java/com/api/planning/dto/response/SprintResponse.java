package com.api.planning.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SprintResponse {
  private Long id;
  private String name;
  private Object card_deck;
  private String creator;
}
