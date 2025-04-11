package com.swyth.hospitalservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the composite primary key for the HospitalBedAvailability entity.
 *
 * This class is marked as embeddable and is used to define a key that links
 * a hospital with a specific medical specialization. It is composed of two
 * fields: hospitalId and medicalSpecializationId.
 *
 * Attributes:
 * - hospitalId: Represents the unique identifier of a hospital.
 * - medicalSpecializationId: Represents the unique identifier of a medical specialization.
 *
 * This class ensures proper handling of composite keys by providing methods
 * for equality and hash code generation through the use of the @EqualsAndHashCode annotation.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HospitalBedAvailabilityId implements Serializable {
    /**
     * Represents the unique identifier of a hospital.
     *
     * This field is part of a composite primary key in the HospitalBedAvailability entity,
     * linking a hospital with a specific medical specialization. It is used to uniquely
     * identify a hospital associated with bed availability data.
     */
    private Long hospitalId;

    /**
     * Represents the unique identifier for a medical specialization.
     *
     * This field is part of the composite primary key in the HospitalBedAvailability entity.
     * It links a hospital with a specific medical specialization to manage and track
     * bed availability data associated with that specialization.
     */
    private Long medicalSpecializationId;
}
