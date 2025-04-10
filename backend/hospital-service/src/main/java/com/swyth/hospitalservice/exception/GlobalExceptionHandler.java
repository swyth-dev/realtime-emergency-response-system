package com.swyth.hospitalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A central exception handling class that intercepts specific application exceptions
 * and generates user-friendly error responses.
 *
 * This class uses Spring's {@link ControllerAdvice} annotation to provide global
 * exception handling, ensuring a consistent response structure and status codes
 * across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link ResourceNotFoundException}, which is thrown when a requested resource
     * cannot be found. Constructs a clear, user-friendly response body containing details about
     * the error and responds with HTTP 404 status.
     *
     * @param ex the {@link ResourceNotFoundException} thrown by the application when a resource
     *           is not found
     * @return a {@link ResponseEntity} containing a map with error details such as the timestamp,
     *         HTTP status code, error description, and an error message
     */
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

    /**
     * Handles the {@link BedUnavailableException}, which is thrown when no beds
     * are available for a specific medical specialization or hospital. Constructs
     * a detailed response body with the timestamp, HTTP status code, error description,
     * and error message to provide a user-friendly error response.
     *
     * @param exception the {@link BedUnavailableException} thrown during operations
     *                  where hospital bed availability cannot be fulfilled
     * @return a {@link ResponseEntity} containing a map with error details and
     *         an HTTP 404 (Not Found) status
     */
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
