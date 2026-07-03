package com.example.demo.exception;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.ValidationErrorResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ValidationErrorResponse> handleBindException(
    BindException exception
  ) {
    Map<String, String> errors = new HashMap<>();

    exception
      .getBindingResult()
      .getFieldErrors()
      .forEach(error -> {
        String field = error.getField();

        if (field.equals("priceRangeValid")) {
          field = "priceRange";
        }

        errors.put(field, error.getDefaultMessage());
      });

    exception
      .getBindingResult()
      .getGlobalErrors()
      .forEach(error -> errors.put("priceRange", error.getDefaultMessage()));

    ValidationErrorResponse response = new ValidationErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Validation failed",
      errors
    );

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFound(
    ProductNotFoundException ex
  ) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND.value(),
      ex.getMessage()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(
    MethodArgumentNotValidException exception
  ) {
    Map<String, String> errors = new HashMap<>();

    exception
      .getBindingResult()
      .getFieldErrors()
      .forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
      );

    ValidationErrorResponse response = new ValidationErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Validation failed",
      errors
    );

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
    EmailAlreadyExistsException exception
  ) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.CONFLICT.value(),
      exception.getMessage()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
