package com.api.planning.dto.response.sprint;


import com.api.common.enums.SprintInclude;
import com.api.planning.dto.response.userstory.UserStoryResponseMapper;
import com.api.planning.entity.Sprint;
import com.api.user.dto.response.UserResponseMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public record SprintResponseMapper(
  UserStoryResponseMapper userStoryResponseMapper,
  UserResponseMapper userResponseMapper
) {

  private static final Set<SprintInclude> NO_INCLUDES = Set.of();

  public SprintResponse toResponse(Sprint sprint) {
    if (sprint == null) {
      return null;
    }

    return toResponse(sprint, NO_INCLUDES);
  }

  public SprintResponse toResponse(Sprint sprint, Set<SprintInclude> includes) {
    SprintResponse base = SprintResponse.builder()
      .id(sprint.getId())
      .name(sprint.getName())
      .cardDeck(sprint.getCardDeck())
      .sequence(sprint.getSequence())
      .creator(sprint.getCreator().getName())
      .build();

    if (includes.contains(SprintInclude.USER_STORIES)) {
      base.setUserStories(sprint.getUserStories()
        .stream()
        .map(us -> userStoryResponseMapper.toResponse(us, includes))
        .toList());
    }

    if (includes.contains(SprintInclude.MEMBERS)) {
      base.setMembers(sprint.getMembers().stream().map(userResponseMapper::toResponse).toList());
    }

    return base;
  }
}


