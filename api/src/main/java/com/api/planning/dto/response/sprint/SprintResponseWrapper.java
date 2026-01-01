package com.api.planning.dto.response.sprint;

import com.api.common.enums.SprintInclude;
import com.api.planning.dto.response.userstory.UserStoryResponseWrapper;
import com.api.planning.entity.Sprint;
import com.api.planning.service.CardDeckService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public record SprintResponseWrapper(
  CardDeckService cardDeckService,
  UserStoryResponseWrapper userStoryResponseWrapper,
  ObjectMapper objectMapper
) {

  public SprintResponse toResponse(Sprint sprint, Set<SprintInclude> includes) {
    JsonNode safeCopy = objectMapper.valueToTree(sprint.getCardDeck());

    SprintResponse base = SprintResponse.builder()
      .id(sprint.getId())
      .name(sprint.getName())
      .cardDeck(cardDeckService.getCardDeckSequence(safeCopy))
      .creator(sprint.getCreator().getName())
      .build();


    if (includes.contains(SprintInclude.USER_STORIES)) {
      base.setUserStories(sprint.getUserStories()
        .stream()
        .map(us -> userStoryResponseWrapper.toResponse(us, includes))
        .toList());
    }

    if (includes.contains(SprintInclude.MEMBERS)) {
      base.setMembers(sprint.getMembers());
    }

    return base;
  }
}


