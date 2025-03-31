package com.swyth.emergencyservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BedReservationResponseDTO {
    private Long id;

    private Long hospitalId;

    private Long medicalSpecializationId;

    private String reservationFirstName;

    private String reservationLastName;

    private String reservationEmail;

    private String reservationPhoneNumber;
}