package com.swyth.emergencyservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a response for a bed reservation.
 *
 * This class is used to encapsulate the response details of a bed reservation,
 * including the reservation's ID, the associated hospital and medical specialization IDs,
 * as well as the personal and contact details of the individual for whom the reservation is made.
 * This DTO is intended for use in response payloads within applications, enabling
 * a clear separation between internal domain models and API responses.
 *
 * Fields:
 * - id: The unique identifier for the bed reservation.
 * - hospitalId: The identifier of the hospital where the reservation is made.
 * - medicalSpecializationId: The identifier of the medical specialization associated with the reservation.
 * - reservationFirstName: The first name of the person for whom the reservation is made.
 * - reservationLastName: The last name of the person for whom the reservation is made.
 * - reservationEmail: The email address of the person for whom the reservation is made.
 * - reservationPhoneNumber: The phone number of the person for whom the reservation is made.
 */
@Data
@NoArgsConstructor
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