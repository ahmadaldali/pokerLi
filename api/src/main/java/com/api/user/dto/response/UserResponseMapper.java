package com.api.user.dto.response;


import com.api.user.entity.User;
import org.springframework.stereotype.Service;


@Service
public record UserResponseMapper() {
  public UserResponse toResponse(User entity) {
    return toResponse(entity, 1);
  }

  private UserResponse toResponse(User entity, int depth) {
    if (entity == null || depth <= 0) {
      return null;
    }

    return UserResponse.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .name(entity.getName())
      .role(entity.getRole())
      .inviter(toResponse(entity.getInviter(), depth - 1))
      .build();
  }
}

