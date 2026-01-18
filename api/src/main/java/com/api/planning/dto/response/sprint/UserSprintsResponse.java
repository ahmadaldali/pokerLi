package com.api.planning.dto.response.sprint;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserSprintsResponse(
  List<SprintResponse> joined,
  List<SprintResponse> joinable
) {
}

