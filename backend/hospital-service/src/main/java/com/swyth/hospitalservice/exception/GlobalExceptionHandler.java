package com.swyth.hospitalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Build a clean response body (no stack traces)
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Resource Not Found");
        body.put("message", ex.getMessage());
        // Return response with 404 status
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Handle BedUnavailableException
    @ExceptionHandler(BedUnavailableException.class)
    public ResponseEntity<Object> handleBedUnavailableException(BedUnavailableException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Bed unavailable");
        body.put("message", exception.getMessage());
        // Return response with 404 status
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
