package com.demobookshop.demobookshopservice.controller;

import com.demobookshop.demobookshopservice.exception.EntityNotFoundException;
import com.demobookshop.demobookshopservice.model.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
      EntityNotFoundException ex, WebRequest request) {
    ApiErrorResponse errorResponse = new ApiErrorResponse(
        ex.getMessage(),
        ex.getMessage(),
        LocalDateTime.now().toString(),
        HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
