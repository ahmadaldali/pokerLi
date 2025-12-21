package com.api.planning.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class CardDeckService {

  public static final List<Integer> DEFAULT_DECK = List.of(1, 2, 3, 5, 8, 13, 21);
  private final ObjectMapper objectMapper;

  public JsonNode getCardDeckSequence(String cardDeckJson) {
    JsonNode defaultSequence = createDefaultSequence();

    try {
      if (cardDeckJson == null || cardDeckJson.trim().isEmpty()) {
        return defaultSequence;
      }

      JsonNode cardDeckObject = objectMapper.readTree(cardDeckJson);
      JsonNode sequenceWithFormat = generateSequence(cardDeckObject);

      ((ObjectNode) cardDeckObject).set("sequence", sequenceWithFormat.get("sequence"));

      return cardDeckObject;

    } catch (Exception e) {
      log.warn("Failed to parse card deck JSON: {}", cardDeckJson, e);
      return defaultSequence;
    }
  }

  private JsonNode generateSequence(JsonNode cardDeckObject) {
    try {
      // Extract fields safely
      double start = cardDeckObject.get("start").asDouble(0);
      int length = cardDeckObject.get("length").asInt(0);
      String format = cardDeckObject.get("format").asText("");
      JsonNode elementsNode = cardDeckObject.get("elements");

      // Replace variables in format (twice like original)
      String processedFormat = replaceVariables(format, elementsNode);
      processedFormat = replaceVariables(processedFormat, elementsNode);

      // Safely evaluate format expression
      double formatValue = safelyEvaluateFormat(processedFormat);

      // Generate sequence
      List<Double> sequence = new ArrayList<>();
      sequence.add(start);

      for (int i = 1; i <= length; i++) {
        sequence.add(sequence.get(i - 1) + formatValue);
      }

      // Return as JSON object matching Laravel structure
      ObjectNode result = objectMapper.createObjectNode();
      result.set("sequence", objectMapper.valueToTree(sequence));
      return result;

    } catch (Exception e) {
      log.warn("Failed to generate sequence", e);
      return createDefaultSequence();
    }
  }

  private String replaceVariables(String format, JsonNode elements) {
    if (elements == null || !elements.isObject()) return format;

    Iterator<String> fieldNames = elements.fieldNames();
    while (fieldNames.hasNext()) {
      String variable = fieldNames.next();
      JsonNode valueNode = elements.get(variable);
      String value = valueNode != null ? valueNode.asText() : "";
      format = format.replace("{" + variable + "}", value);
    }
    return format;
  }

  private double safelyEvaluateFormat(String expression) {
    // Simple safe math expression evaluator
    try {
      expression = expression.replaceAll("[^0-9+\\-*/(). ]", "");
      ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
      Object result = engine.eval(expression);
      return result instanceof Number ? ((Number) result).doubleValue() : 0;
    } catch (Exception e) {
      log.warn("Failed to evaluate format expression: {}", expression, e);
      return 0;
    }
  }

  private JsonNode createDefaultSequence() {
    ObjectNode defaultNode = objectMapper.createObjectNode();
    defaultNode.set("sequence", objectMapper.valueToTree(DEFAULT_DECK));

    return defaultNode;
  }
}
