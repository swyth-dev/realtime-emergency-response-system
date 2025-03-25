package com.swyth.hospitalservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmergencyLocationDTO {
    @NotNull(message = "Medical specialization ID must not be null")
    private Long medicalSpecializationId;

    @NotNull(message = "Latitude must not be null")
    private Double latitude;

    @NotNull(message = "Longitude must not be null")
    private Double longitude;

    // Getters and Setters
}