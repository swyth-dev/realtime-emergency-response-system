package com.swyth.emergencyservice.exception;

/**
 * BedUnavailableException is a custom runtime exception that is thrown
 * when no available beds are found in a hospital for a specific medical
 * specialization.
 *
 * This exception typically represents a scenario where the requested
 * resource (e.g., hospital bed) is not available, and can be utilized in
 * applications to provide meaningful error messages and responses for clients.
 *
 * The exception can be handled globally or at a method level, providing
 * additional context such as timestamps, HTTP status codes, and error messages.
 *
 * Constructors:
 * - BedUnavailableException(String message): Constructs the exception with a
 *   specific detail message about the unavailability of resources.
 */
public class BedUnavailableException extends RuntimeException {
    public BedUnavailableException(String message) {
        super(message);
    }
}
