package com.swyth.hospitalservice.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * This exception is thrown when a requested resource cannot be found.
 *
 * It is typically used in scenarios where a resource, such as a database entity,
 * is expected but not available, either due to being non-existent or removed.
 *
 * Example use cases include:
 * - Throwing this exception in service methods to indicate that the expected resource does not exist.
 * - Intercepting this exception in a global exception handler to return a meaningful response to clients.
 *
 * Extends {@link RuntimeException} to allow unchecked exception handling.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to parent RuntimeException
    }
}