package com.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteUser {

  @NotBlank(message = "{error.email.required}")
  @Email(message = "{error.email.invalid}")
  private String email;

}
