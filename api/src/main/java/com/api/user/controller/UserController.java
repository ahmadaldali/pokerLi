package com.api.user.controller;


import com.api.common.dto.SuccessResponse;
import com.api.planning.dto.response.sprint.SprintResponse;
import com.api.planning.dto.response.sprint.UserSprintsResponse;
import com.api.planning.service.SprintService;
import com.api.user.dto.request.InviteUser;
import com.api.user.dto.response.UserResponse;
import com.api.user.service.CustomUserDetails;
import com.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SprintService sprintService;

  @GetMapping("/me")
  public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userService.me(userDetails.getUserId()));
  }

  @PostMapping("/invite")
  public ResponseEntity<SuccessResponse> invite(@Valid @RequestBody InviteUser request, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userService.inviteUser(request.getEmail(), userDetails.getUserId()));
  }

  @GetMapping("/sprints")
  public ResponseEntity<UserSprintsResponse> getUserSprints(@RequestParam(required = false) Set<String> membership, @RequestParam(required = false) Set<String> include, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(sprintService.getUserSprints(userDetails.getUserId(), membership, include));
  }

}
