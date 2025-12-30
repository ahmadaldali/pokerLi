package com.api.planning.dto.response.sprint;

import com.api.planning.entity.Sprint;
import com.api.planning.service.CardDeckService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public record SprintResponseWrapper(
  CardDeckService cardDeckService,
  ObjectMapper objectMapper
) {

  public SprintResponse toResponse(Sprint sprint) {
    JsonNode safeCopy = objectMapper.valueToTree(sprint.getCardDeck());

    return SprintResponse.builder()
      .id(sprint.getId())
      .name(sprint.getName())
      .cardDeck(cardDeckService.getCardDeckSequence(safeCopy))
      .creator(sprint.getCreator().getName())
      .build();
  }
}


