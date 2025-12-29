package com.api.planning.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSprintRequest {
  @NotBlank(message = "{error.name.required}")
  @Size(min = 3, max = 50, message = "{error.name.length_3_50}")
  private String name;

  @NotNull(message = "{error.card_deck.required}")
  private JsonNode cardDeck;
}



