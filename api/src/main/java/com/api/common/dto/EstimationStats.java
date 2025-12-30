package com.api.common.dto;

public record EstimationStats(
  double total,
  double average,
  long count
) {}
