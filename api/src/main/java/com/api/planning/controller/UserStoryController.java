package com.api.planning.controller;


import com.api.common.dto.SuccessResponse;
import com.api.planning.dto.request.EstimateUserStoryRequest;
import com.api.planning.dto.response.userstory.UserStoryResponse;
import com.api.planning.service.SprintService;
import com.api.planning.service.UserStoryService;
import com.api.user.service.CustomUserDetails;
import com.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@RequiredArgsConstructor
public class UserStoryController {

  private final UserStoryService userStoryService;
  private final SprintService sprintService;

  @PostMapping("/{id}/vote")
  public ResponseEntity<SuccessResponse> vote(@Valid @RequestBody EstimateUserStoryRequest request, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.vote(id,  userDetails.getUserId(), request.getEstimation()));
  }

  @PostMapping("/{id}/un-vote")
  public ResponseEntity<SuccessResponse> unVote(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.unVote(id,  userDetails.getUserId()));
  }

  @PostMapping("/{id}/reveal")
  public ResponseEntity<SuccessResponse> reveal(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.reveal(id,  userDetails.getUserId()));
  }

  @PostMapping("/{id}/vote-again")
  public ResponseEntity<SuccessResponse> voteAgain(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.voteAgain(id,  userDetails.getUserId()));
  }

  @PostMapping("/{id}/select")
  public ResponseEntity<UserStoryResponse> select(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    UserStoryResponse response = userStoryService.select(id,  userDetails.getUserId());

    sprintService.sendSprintUpdatedEvent(response.getSprintId());

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SuccessResponse> delete(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.delete(id,  userDetails.getUserId()));
  }
}
