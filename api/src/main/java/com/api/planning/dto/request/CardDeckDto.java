package com.api.planning.dto.request;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDeckDto {
  private String format;
  private int length;
  private Map<String, Integer> elements;
  private int start;
}
