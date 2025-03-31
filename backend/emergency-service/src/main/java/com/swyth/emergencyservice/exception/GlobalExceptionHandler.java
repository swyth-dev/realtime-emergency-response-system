package com.swyth.emergencyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle bad formatted requests
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleWebExchangeBindException(WebExchangeBindException ex, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

        // Add basic information excluding "message" and "trace"
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("path", exchange.getRequest().getPath().toString());

        // Add validation errors
        Map<String, String> validationErrors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });
        response.put("errors", validationErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle BedUnavailableException
    @ExceptionHandler(BedUnavailableException.class)
    public ResponseEntity<Object> handleBedUnavailableException(BedUnavailableException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Resource Not Found");
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
