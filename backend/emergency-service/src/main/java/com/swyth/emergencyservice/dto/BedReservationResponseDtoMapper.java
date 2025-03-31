package com.swyth.emergencyservice.dto;

import com.swyth.emergencyservice.entity.BedReservation;

public class BedReservationResponseDtoMapper {

    public BedReservationResponseDtoMapper() {
    }

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
