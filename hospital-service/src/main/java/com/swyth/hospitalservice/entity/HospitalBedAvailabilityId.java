package com.swyth.hospitalservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HospitalBedAvailabilityId implements Serializable {
    private Long hospitalId;
    private Long medicalSpecializationId;
}
