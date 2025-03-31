package com.swyth.emergencyservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// TODO: refactor to bed reservation Request/Payload
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
