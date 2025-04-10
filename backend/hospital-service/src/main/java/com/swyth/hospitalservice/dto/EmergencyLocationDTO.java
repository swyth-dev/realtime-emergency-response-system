package com.swyth.hospitalservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The EmergencyLocationDTO class is a data transfer object used to encapsulate the location
 * and medical specialization information required for identifying the nearest hospital.
 *
 * It contains the following fields:
 * - medicalSpecializationId: Represents the unique identifier for a specific medical specialization.
 * - latitude: Specifies the latitude coordinate of the location.
 * - longitude: Specifies the longitude coordinate of the location.
 *
 * This class provides the necessary data for operations such as calculating proximity to
 * hospitals based on geographical coordinates and filtering by medical specialization.
 *
 * Validation annotations are applied to ensure that the fields are not null when the object
 * is used in service or controller layers. This helps prevent invalid or incomplete data
 * from being processed.
 *
 * The class is typically utilized as an input parameter in API endpoints that deal with
 * location-based querying, such as finding the nearest hospital with available beds for
 * a specified medical specialty.
 */
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