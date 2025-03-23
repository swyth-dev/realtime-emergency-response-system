package com.swyth.hospitalservice.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to parent RuntimeException
    }
}