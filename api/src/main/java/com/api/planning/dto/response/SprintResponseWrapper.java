package com.api.planning.dto.response;

import com.api.planning.entity.Sprint;
import com.api.planning.service.CardDeckService;
import org.springframework.stereotype.Service;

@Service
public record SprintResponseWrapper(CardDeckService cardDeckService) {
  public SprintResponse toResponse(Sprint sprint) {
    return new SprintResponse(
      sprint.getId(),
      sprint.getName(),
      cardDeckService.getCardDeckSequence(sprint.getCardDeck()),
      sprint.getCreator().getName()
    );
  }
}
