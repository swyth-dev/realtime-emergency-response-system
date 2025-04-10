package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents the availability of hospital beds for a specific medical specialization in a particular hospital.
 *
 * This class maps the relationship between a hospital and a medical specialization,
 * maintaining the current number of beds available for that specialization in the hospital.
 *
 * Key Features:
 * - Identified by a composite primary key represented by {@code HospitalBedAvailabilityId}.
 * - Maintains a many-to-one relationship with the {@code Hospital} entity.
 * - Maintains a many-to-one relationship with the {@code MedicalSpecialization} entity.
 * - Stores the current number of beds available for the specified specialization in the associated hospital.
 *
 * Relationships:
 * - The {@code hospital} field represents the associated hospital for this bed availability record.
 * - The {@code specialization} field represents the associated medical specialization for this bed availability record.
 * - Both relationships are eagerly fetched to ensure the related entities are available when this entity is retrieved.
 *
 * Annotations:
 * - The {@code @Entity} annotation marks this class as a persistent and managed entity.
 * - The {@code @Table} annotation specifies the table name in the database.
 * - The {@code @EmbeddedId} annotation specifies the composite primary key for the entity.
 * - Lombok annotations such as {@code @Data}, {@code @NoArgsConstructor}, and {@code @AllArgsConstructor}
 *   are used for generating boilerplate methods like constructors, getters, setters, equals, and hashCode.
 *
 * Attributes:
 * - id: Composite primary key linking a hospital and a medical specialization.
 * - hospital: The hospital associated with this record.
 * - specialization: The medical specialization associated with this record.
 * - availableBeds: The number of currently available beds for the given hospital and specialization.
 */
@Entity
@Table(name = "hospital_bed_availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class HospitalBedAvailability {

    @EmbeddedId
    private HospitalBedAvailabilityId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("hospitalId")
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("medicalSpecializationId")
    @JoinColumn(name = "medical_specialization_id")
    private MedicalSpecialization specialization;

    // Additional field
    private int availableBeds;
}
