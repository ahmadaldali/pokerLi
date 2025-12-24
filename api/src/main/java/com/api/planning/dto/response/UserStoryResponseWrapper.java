package com.api.planning.dto.response;

import com.api.planning.entity.UserStory;
import org.springframework.stereotype.Service;

@Service
public record UserStoryResponseWrapper() {
  public UserStoryResponse toResponse(UserStory userStory) {
    return new UserStoryResponse(
      userStory.getId()
    );
  }
}
