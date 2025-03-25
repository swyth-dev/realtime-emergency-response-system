package com.swyth.hospitalservice.dto;

import lombok.Data;

@Data
public class BedReservationDTO {
    private Long id;
    private Long hospitalId;
    private Long medicalSpecializationId;
}
