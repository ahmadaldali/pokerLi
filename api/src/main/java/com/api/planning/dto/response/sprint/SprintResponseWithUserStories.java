package com.api.planning.dto.response.sprint;

import com.api.planning.dto.response.userstory.UserStoryResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class SprintResponseWithUserStories {
  private SprintResponse sprint;
  private List<UserStoryResponse> userStories;
}

