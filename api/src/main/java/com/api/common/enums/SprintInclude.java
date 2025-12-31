package com.api.common.enums;

import com.api.common.exception.ValidationException;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum SprintInclude {

  USER_STORIES("userStories"),
  ESTIMATION_RESULTS("estimationResults"),
  ESTIMATIONS("estimations");

  private final String value;

  SprintInclude(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public static SprintInclude fromValue(String value) {
    for (SprintInclude include : values()) {
      if (include.value.equals(value)) {
        return include;
      }
    }

    throw new ValidationException("error.include.enum.invalid");
  }

  public static Set<SprintInclude> parse(Set<String> include) {
    if (include == null || include.isEmpty()) {
      return EnumSet.noneOf(SprintInclude.class);
    }

    return include.stream()
      .map(SprintInclude::fromValue)
      .collect(Collectors.toSet());
  }

}
