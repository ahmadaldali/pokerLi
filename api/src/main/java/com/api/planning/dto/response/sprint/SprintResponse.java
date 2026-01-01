package com.api.planning.dto.response.sprint;


import com.api.planning.dto.response.userstory.UserStoryResponse;
import com.api.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class SprintResponse {
  private Long id;
  private String name;
  private JsonNode cardDeck;
  private String creator;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<UserStoryResponse> userStories;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Set<User> members;
}
