package com.api.user.dto.response;


import com.api.user.entity.User;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class UserResponseWrapper {
  public UserResponse toResponse(User entity) {
    return UserResponse.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .name(entity.getName())
      .build();
  }
}
