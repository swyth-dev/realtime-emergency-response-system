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

    /**
     * Represents the composite primary key for the {@code HospitalBedAvailability} entity.
     *
     * The {@code id} field is marked with the {@code @EmbeddedId} annotation, indicating
     * that it serves as the composite key for the entity. It combines details about the
     * hospital and medical specialization to uniquely identify a hospital's bed availability
     * record.
     *
     * Attributes:
     * - Contains the {@code HospitalBedAvailabilityId} object which defines the composite
     *   key structure.
     *
     * Purpose:
     * - Facilitates the identification of bed availability records by combining hospital
     *   and medical specialization identifiers.
     *
     * Key Usage:
     * - Essential for mapping and querying hospital bed availability details in a relational
     *   database.
     */
    @EmbeddedId
    private HospitalBedAvailabilityId id;

    /**
     * Represents the associated hospital entity in the context of hospital bed availability.
     *
     * This field establishes a many-to-one relationship between the `HospitalBedAvailability`
     * entity and the `Hospital` entity. Each bed availability record is linked to a specific
     * hospital, enabling the identification and tracking of bed availability based on the hospital.
     *
     * Features:
     * - The relationship is eagerly fetched to ensure that the associated `Hospital` entity
     *   is available whenever the `HospitalBedAvailability` entity is retrieved.
     * - The mapping is identified by the `hospitalId` attribute in the composite key
     *   `HospitalBedAvailabilityId` using the `@MapsId` annotation.
     * - The `@JoinColumn` annotation specifies the foreign key column (`hospital_id`) in the
     *   `HospitalBedAvailability` table that references the primary key of the `Hospital` entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("hospitalId")
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    /**
     * Represents the medical specialization associated with a specific hospital bed availability record.
     *
     * This field establishes a many-to-one relationship between the {@code HospitalBedAvailability} entity
     * and the {@code MedicalSpecialization} entity. It links the hospital bed availability to a particular
     * medical specialization.
     *
     * Key Features:
     * - This relationship is mapped using the {@code medicalSpecializationId} field in the composite primary key
     *   {@code HospitalBedAvailabilityId}.
     * - Eagerly fetched to ensure that the associated {@code MedicalSpecialization} entity is loaded
     *   together with the {@code HospitalBedAvailability} entity.
     * - Configured with the {@code @JoinColumn} annotation to specify the foreign key column
     *   {@code medical_specialization_id} in the database table.
     *
     * Annotations:
     * - {@code @ManyToOne}: Specifies a many-to-one relationship between this entity and the {@code MedicalSpecialization}.
     * - {@code @MapsId}: Indicates that the primary key of the current entity includes
     *   the {@code medicalSpecializationId} from the composite primary key.
     * - {@code @JoinColumn}: Maps the {@code medicalSpecializationId} field to its corresponding foreign key column.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("medicalSpecializationId")
    @JoinColumn(name = "medical_specialization_id")
    private MedicalSpecialization specialization;


    /**
     * Stores the number of beds currently available in the hospital for a specific medical specialization.
     *
     * This field represents the dynamic count of beds that are ready to accommodate patients
     * in the context of a specific `HospitalBedAvailability` record. It provides critical
     * information for hospital management and patient allocation systems.
     *
     * Purpose:
     * - Tracks the availability of hospital beds associated with a particular specialization.
     * - Facilitates real-time monitoring and reporting of bed capacity.
     *
     * Typical Use Cases:
     * - Used in applications that require hospital capacity and bed allocation details.
     * - Serves as a key attribute for operational decisions in healthcare systems.
     */
    private int availableBeds;
}
