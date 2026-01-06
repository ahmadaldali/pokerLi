package com.api.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
      .forEach(error -> errors.put(error.getField(),
        error.getDefaultMessage()
      ));

    errors.put("error", "BAD_REQUEST");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations()
      .forEach(cv -> errors.put(cv.getPropertyPath().toString(), cv.getMessage()));

    errors.put("error", "BAD_REQUEST");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("method", ex.getMethod());

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errors);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
    Map<String, String> errors = new HashMap<>();
    String message = messageSource.getMessage(ex.getCode(), null, Locale.getDefault());
    errors.put("error", message);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Map<String, String>> handleForbiddenException(ForbiddenException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", "ACTION_NOT_ALLOWED");

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
  }

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<Map<String, String>> handleUnAuthorizedException(UnAuthorizedException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", "UN_AUTHORIZED");

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(EntityNotFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", "NOT_FOUND");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(NoResourceFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", "NOT_FOUND");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrityException(DataIntegrityViolationException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleException(Exception ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
  }
}

