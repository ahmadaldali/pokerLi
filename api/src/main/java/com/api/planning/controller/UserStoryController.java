package com.api.planning.controller;


import com.api.common.dto.SuccessResponse;
import com.api.planning.dto.request.EstimateUserStoryRequest;
import com.api.planning.dto.response.UserStoryResponse;
import com.api.planning.service.UserStoryService;
import com.api.user.service.CustomUserDetails;
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


  @PostMapping("/{id}/vote")
  public ResponseEntity<SuccessResponse> vote(@Valid @RequestBody EstimateUserStoryRequest request, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userStoryService.vote(id,  userDetails.getUserId(), request.getEstimation()));
  }

}
