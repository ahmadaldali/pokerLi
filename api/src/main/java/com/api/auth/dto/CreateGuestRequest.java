package com.api.auth.dto;

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
public class CreateGuestRequest {
  @NotBlank(message = "{error.name.required}")
  @Size(min = 3, max = 50, message = "{error.name.length_3_50}")
  private String name;

  @NotBlank(message = "{error.guest_id.required}")
  private String guestId;
}
