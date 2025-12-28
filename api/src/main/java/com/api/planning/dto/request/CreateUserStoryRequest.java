package com.api.planning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserStoryRequest {
  @NotBlank(message = "{error.name.required}")
  @Size(min = 3, max = 50, message = "{error.name.length_3_50}")
  private String name;

  @Size(max = 1000, message = "{error.description.max_1000}")
  private String description;

  @URL(message = "{error.link.invalid_url}")
  @Size(max = 500, message = "{error.link.max_500}")
  private String link;
}


