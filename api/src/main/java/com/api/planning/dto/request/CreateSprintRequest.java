package com.api.planning.dto.request;

import jakarta.validation.constraints.NotBlank;
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
  private String name;

  @NotBlank(message = "{error.card_deck.required}")
  private String card_deck;
}


