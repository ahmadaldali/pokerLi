package com.api.user.dto.response;


import com.api.common.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
  private Long id;
  private String name;
  private String email;
  private UserRole role;
  private UserResponse inviter;
}
