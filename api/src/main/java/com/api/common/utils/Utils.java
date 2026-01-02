package com.api.common.utils;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class Utils {

  public static JsonNode safeCopyJsonNode(JsonNode node) {
    return node == null ? null : node.deepCopy();
  }

  public static List<Double> jsonNodeToDoubleList(JsonNode node) {
    if (!node.isArray()) {
      return new ArrayList<>();
    }

    List<Double> doubles = new ArrayList<>();
    StreamSupport.stream(node.spliterator(), false)
      .forEach(child -> doubles.add(child.asDouble()));

    return doubles;
  }

}
