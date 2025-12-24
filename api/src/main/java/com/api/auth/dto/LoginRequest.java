package com.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
  @NotBlank(message = "{error.email.required}")
  @Email(message = "{error.email.invalid}")
  private String email;

  @NotBlank(message = "{error.password.required}")
  private String password;
}
