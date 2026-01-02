package com.api.user.controller;


import com.api.common.dto.SuccessResponse;
import com.api.user.dto.request.InviteUser;
import com.api.user.dto.response.UserResponse;
import com.api.user.service.CustomUserDetails;
import com.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userService.me(userDetails.getUserId()));
  }

  @PostMapping("/invite")
  public ResponseEntity<SuccessResponse> invite(@Valid @RequestBody InviteUser request, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(userService.inviteUser(request.getEmail(), userDetails.getUserId()));
  }
}
