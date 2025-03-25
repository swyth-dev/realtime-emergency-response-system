package com.swyth.emergencyservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BedReservationDTO {

    @NotNull(message = "Hospital ID must not be null")
    private Long hospitalId;

    @NotNull(message = "Medical Specialization ID must not be null")
    private Long medicalSpecializationId;

}
