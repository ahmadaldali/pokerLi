package com.api.planning.controller;

import com.api.common.dto.SuccessResponse;
import com.api.planning.dto.request.CreateSprintRequest;
import com.api.planning.dto.request.CreateUserStoryRequest;
import com.api.planning.dto.response.SprintResponse;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.service.SprintService;
import com.api.user.service.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sprints")
@RequiredArgsConstructor
public class SprintController {

  private final SprintService sprintService;

  @PostMapping("")
  public ResponseEntity<SuccessResponse> create(@Valid @RequestBody CreateSprintRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(sprintService.createSprint(request.getName(), request.getCard_deck(), userDetails.getUserId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SprintResponse> get(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(sprintService.getSprint(id, userDetails.getUserId()));
  }

  @PostMapping("/{id}/join")
  public ResponseEntity<SuccessResponse> join(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(sprintService.join(id,  userDetails.getUserId()));
  }

  // user stories
  @PostMapping("/{id}/start-new-voting")
  public ResponseEntity<UserStoryResponse> startNewVoting(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    // create the generic user story for the sprint (no info just voting)
    return ResponseEntity.ok(sprintService.createUserStory(id,  userDetails.getUserId(), null, null, null));
  }

  @PostMapping("{id}/user-stories")
  public ResponseEntity<UserStoryResponse> createUserStory(@PathVariable Long id, @Valid @RequestBody CreateUserStoryRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(sprintService.createUserStory(id, userDetails.getUserId(), request.getName(), request.getDescription(), request.getLink()));
  }

}
