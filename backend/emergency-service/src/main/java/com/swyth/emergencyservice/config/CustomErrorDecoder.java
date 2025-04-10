package com.swyth.emergencyservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.Map;

/**
 * CustomErrorDecoder is an implementation of the ErrorDecoder interface
 * used to decode and handle HTTP error responses in a Feign client.
 * It parses the error details from the response body and maps specific
 * HTTP status codes to custom exceptions.
 */
public class CustomErrorDecoder implements ErrorDecoder {

    // Jackson ObjectMapper to parse JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        String errorMessage = "Unknown error occurred"; // Default fallback message

        // Try to extract the original message from external API response
        try {
            // Parse the JSON response body
            if (response.body() != null) {
                InputStream bodyStream = response.body().asInputStream();
                Map<String, String> errorResponse = objectMapper.readValue(bodyStream, Map.class);

                String message = errorResponse.getOrDefault("message", "No details provided");

                errorMessage = message;
            }
        } catch (Exception ex) {
            // Log or handle exception if parsing fails
            ex.printStackTrace();
            errorMessage = "Error parsing error response: " + ex.getMessage();
        }

        // Handle specific HTTP status codes
        return switch (status) {
            case NOT_FOUND -> // 404
                    new BedUnavailableException(errorMessage);
            case BAD_REQUEST -> // 400
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: " + errorMessage);
            case INTERNAL_SERVER_ERROR -> // 500
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred. " + errorMessage);
            default ->
                // For all other errors, map to a generic exception
                    new ResponseStatusException(status, "Unexpected error: " + response.reason());
        };
    }
}
