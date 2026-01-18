package com.api.planning.controller;


import com.api.planning.dto.response.sprint.SprintResponse;
import com.api.planning.service.SprintService;
import com.api.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

  private final SprintService sprintService;

  @GetMapping("/sprints/{id}")
  public ResponseEntity<SprintResponse> getSprint(@PathVariable Long id, @RequestParam(required = false) Set<String> include) {
    return ResponseEntity.ok(sprintService.get(id, include));
  }
}
