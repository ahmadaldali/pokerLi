package com.api.planning.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSprintRequest {
  @NotBlank(message = "{error.name.required}")
  @Size(min = 3, max = 50, message = "{error.name.length_3_50}")
  private String name;

  // Optional
  private JsonNode cardDeck;

  // Optional, ordered, numeric sequence
  @Size(max = 10, message = "{error.sequence.max_10_elements}")
  private List<
        Double
      > sequence;
}



