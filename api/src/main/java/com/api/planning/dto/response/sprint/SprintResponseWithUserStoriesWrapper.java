package com.api.planning.dto.response.sprint;

import com.api.planning.dto.response.userstory.UserStoryResponseWrapper;
import com.api.planning.entity.Sprint;
import com.api.planning.service.CardDeckService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public record SprintResponseWithUserStoriesWrapper(
  CardDeckService cardDeckService,
  UserStoryResponseWrapper userStoryResponseWrapper,
  ObjectMapper objectMapper
) {

  public SprintResponseWithUserStories toResponse(Sprint sprint) {
    JsonNode safeCopy = objectMapper.valueToTree(sprint.getCardDeck());

    SprintResponse base = SprintResponse.builder()
      .id(sprint.getId())
      .name(sprint.getName())
      .cardDeck(cardDeckService.getCardDeckSequence(safeCopy))
      .creator(sprint.getCreator().getName())
      .build();

    return SprintResponseWithUserStories.builder()
      .sprint(base)
      .userStories(
        sprint.getUserStories()
          .stream()
          .map(userStoryResponseWrapper::toResponse)
          .toList()
      )
      .build();
  }
}
