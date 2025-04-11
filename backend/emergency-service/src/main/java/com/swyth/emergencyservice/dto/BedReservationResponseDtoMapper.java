package com.swyth.emergencyservice.dto;

import com.swyth.emergencyservice.entity.BedReservation;

/**
 * This class provides mapping functionality to convert a {@code BedReservation} entity
 * into a {@code BedReservationResponseDTO}.
 *
 * The purpose of the {@code BedReservationResponseDtoMapper} is to extract
 * relevant fields from the {@code BedReservation} entity and package them
 * into a data transfer object ({@code BedReservationResponseDTO})
 * for use in external layers such as APIs or client responses.
 *
 * The mapper ensures consistency and reduces the complexity of
 * transforming domain objects into DTOs by providing a dedicated method
 * for the transformation process.
 */
public class BedReservationResponseDtoMapper {

    /**
     * Converts a {@code BedReservation} entity into a {@code BedReservationResponseDTO}.
     *
     * This method extracts the relevant fields from the {@code BedReservation} entity
     * and constructs a corresponding {@code BedReservationResponseDTO} to provide
     * a simplified data transfer representation of the reservation.
     *
     * @param reservation the {@code BedReservation} entity to be converted
     * @return a {@code BedReservationResponseDTO} containing the reservation details
     */
    public static BedReservationResponseDTO convertToDTO(BedReservation reservation) {
        return new BedReservationResponseDTO(
                reservation.getId(),
                reservation.getHospitalId(),
                reservation.getMedicalSpecializationId(),
                reservation.getReservationFirstName(),
                reservation.getReservationLastName(),
                reservation.getReservationEmail(),
                reservation.getReservationPhoneNumber()
        );
    }

}
