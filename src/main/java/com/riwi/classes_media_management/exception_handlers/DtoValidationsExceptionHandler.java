package com.riwi.classes_media_management.exception_handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DtoValidationsExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException exception) {

    ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(
        exception.getStatusCode(), exception.getMessage());

    problemDetails.setProperty("errors", exception.getAllErrors()
        .stream()
        .map(err -> {
          return Map.ofEntries(
              Map.entry("field", ((FieldError) err).getField()),
              Map.entry("error", err.getDefaultMessage()));
        })
        .toArray());

    return problemDetails;
  }
}
