package com.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
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
  private String name;

  @NotBlank(message = "{error.guest_id.required}")
  private String guestId;
}
