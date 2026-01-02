package com.api.planning.service;


import com.api.common.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;
import java.util.stream.StreamSupport;



@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class CardDeckService {

  public static final List<Double> DEFAULT_DECK = List.of(1d, 2d, 3d, 5d, 8d, 13d, 21d, 33d);
  private final ObjectMapper objectMapper;
  private static final ExpressionParser PARSER = new SpelExpressionParser();

  // check a value
  public boolean hasValue(JsonNode cardDeckJson, Integer estimation) {
    JsonNode cardDeckSequence = this.getCardDeckSequence(cardDeckJson);
    JsonNode sequence = cardDeckSequence.get("sequence");

    return StreamSupport.stream(sequence.spliterator(), false).anyMatch(n -> n.asInt() == estimation);
  }

  // return the sequence
  public List<Double> getSequence(JsonNode cardDeck, List<Double> sequence) {
    if (sequence != null && !sequence.isEmpty()) {
      return sequence;  // User-provided takes precedence
    }

    if (cardDeck != null) {
      JsonNode processedDeck = this.getCardDeckSequence(cardDeck);
      return Utils.jsonNodeToDoubleList(processedDeck.get("sequence"));
    }

    return new ArrayList<>(CardDeckService.DEFAULT_DECK);  // Safe copy of default
  }


  // private
  private JsonNode getCardDeckSequence(JsonNode cardDeckJson) {
    JsonNode defaultSequence = createDefaultSequence();

    try {
      if (cardDeckJson == null) {
        return defaultSequence;
      }

      JsonNode cardDeckObject;
      cardDeckObject = cardDeckJson;

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
      double start = cardDeckObject.path("start").asDouble(0);
      int length = cardDeckObject.path("length").asInt(0);
      String format = cardDeckObject.path("format").asText("");
      JsonNode elementsNode = cardDeckObject.path("elements");

      // Replace variables in format (twice like original PHP)
      String processedFormat = replaceVariables(format, elementsNode);
      processedFormat = replaceVariables(processedFormat, elementsNode);

      // Safely evaluate format expression (SpEL)
      double formatValue = safelyEvaluateFormat(processedFormat, elementsNode);

      // Generate sequence
      List<Double> sequence = new ArrayList<>();
      sequence.add(start);

      for (int i = 1; i <= length; i++) {
        sequence.add(sequence.get(i - 1) + formatValue);
      }

      // Return Laravel-like structure
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



  private double safelyEvaluateFormat(String expression, JsonNode elements) {
    if (expression == null || expression.isBlank()) {
      return 0;
    }

    try {
      // Declare variables first (string names only)
      ExpressionBuilder builder = new ExpressionBuilder(expression);

      // Collect variable values in a map
      Map<String, Double> varValues = new HashMap<>();
      if (elements != null && elements.isObject()) {
        Iterator<String> fieldNames = elements.fieldNames();
        while (fieldNames.hasNext()) {
          String varName = fieldNames.next();
          JsonNode valueNode = elements.get(varName);
          if (valueNode != null && valueNode.isNumber()) {
            builder.variable(varName);  // Declare
            varValues.put(varName, valueNode.asDouble());  // Store value
          }
        }
      }

      Expression exp = builder.build()
        .setVariables(varValues);  // Set all at once

      return exp.evaluate();

    } catch (Exception e) {
      log.warn("Failed to evaluate expression: '{}': {}", expression, e.getMessage());
      return 0;
    }
  }

  private JsonNode createDefaultSequence() {
    ObjectNode defaultNode = objectMapper.createObjectNode();
    defaultNode.set("sequence", objectMapper.valueToTree(DEFAULT_DECK));

    return defaultNode;
  }
}
