package com.api.event.publisher;


import com.api.planning.dto.response.userstory.UserStoryResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserStoryEventPublisher {

  private final SimpMessagingTemplate messagingTemplate;

  public UserStoryEventPublisher(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public void publishUserStoryUpdated(UserStoryResponse details) {
    messagingTemplate.convertAndSend(
      "/topic/user-story-channel",
      Map.of("user_story_details", details)
    );
  }
}
