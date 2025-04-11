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

/**
 * GlobalExceptionHandler is a centralized exception handler class that provides
 * consistent error responses for exceptions occurring throughout the application.
 *
 * This class uses the @ControllerAdvice annotation to enable global exception
 * handling across the application's controllers. It contains methods annotated
 * with @ExceptionHandler to handle specific exceptions and tailor the error
 * responses accordingly.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handles WebExchangeBindException to provide detailed error responses for bad formatted requests.
     *
     * This method constructs a standardized error response containing a timestamp, HTTP status code,
     * error type, request path, and validation errors. The validation errors include details about
     * fields that failed validation along with their corresponding error messages.
     *
     * @param ex the WebExchangeBindException that occurred, containing details of the validation errors
     * @param exchange the ServerWebExchange object representing the current web exchange, used to retrieve the request path
     * @return a ResponseEntity containing the error response with validation error details and an HTTP status code of 400 (Bad Request)
     */
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

    /**
     * Handles the BedUnavailableException and constructs a custom error response.
     *
     * This method is triggered whenever a BedUnavailableException is thrown in the
     * application. It returns a standardized error response with relevant details such as
     * timestamp, HTTP status code, error type, and a descriptive message.
     *
     * @param exception the BedUnavailableException that occurred
     * @return a ResponseEntity containing the error response with details and an HTTP status code of 404 (Not Found)
     */
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
