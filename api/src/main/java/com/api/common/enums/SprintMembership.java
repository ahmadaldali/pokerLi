package com.api.common.enums;

import com.api.common.exception.ValidationException;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum SprintMembership {
  JOINED("joined"),
  JOINABLE("joinable"),
  ALL("all");

  private final String value;

  SprintMembership(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public static SprintMembership fromValue(String value) {
    for (SprintMembership include : values()) {
      if (include.value.equals(value)) {
        return include;
      }
    }

    throw new ValidationException("error.include.enum.invalid");
  }

  public static Set<SprintMembership> parse(Set<String> include) {
    if (include == null || include.isEmpty()) {
      return EnumSet.noneOf(SprintMembership.class);
    }

    return include.stream()
      .map(SprintMembership::fromValue)
      .collect(Collectors.toSet());
  }
}
