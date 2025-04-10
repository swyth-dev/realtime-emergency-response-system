package com.swyth.emergencyservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object for handling bed reservation requests.
 *
 * This class encapsulates the details required to create a bed reservation for a specific hospital
 * and medical specialization. It includes information about the hospital, medical specialization,
 * and the personal details of the individual making the reservation.
 *
 * Validation annotations are included for ensuring the integrity and correctness
 * of the data provided in the request.
 */
@Data
public class BedReservationDTO {

    @NotNull(message = "Hospital ID must not be null")
    private Long hospitalId;

    @NotNull(message = "Medical Specialization ID must not be null")
    private Long medicalSpecializationId;

    @NotNull(message = "First name cannot be null")
    private String reservationFirstName;

    @NotNull(message = "Last name cannot be null")
    private String reservationLastName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Please provide a valid email address")
    private String reservationEmail;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(
            regexp = "\\+?[1-9]\\d{1,14}",
            message = "Phone number must be in valid international format (e.g., +123456789)"
    )
    private String reservationPhoneNumber;
}
