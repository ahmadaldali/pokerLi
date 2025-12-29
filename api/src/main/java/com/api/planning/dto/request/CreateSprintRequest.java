package com.api.planning.dto.request;

import jakarta.validation.constraints.NotBlank;
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

  @NotBlank(message = "{error.card_deck.required}")
  private String cardDeck;
}



