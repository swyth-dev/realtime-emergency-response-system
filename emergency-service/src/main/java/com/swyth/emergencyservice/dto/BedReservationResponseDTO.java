package com.swyth.emergencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BedReservationResponseDTO {
    private Long id;

    private Long hospitalId;

    private Long medicalSpecializationId;
}