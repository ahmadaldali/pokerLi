package com.api.planning.dto.response.sprint;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SprintResponse {
  private Long id;
  private String name;
  private JsonNode cardDeck;
  private String creator;
}
