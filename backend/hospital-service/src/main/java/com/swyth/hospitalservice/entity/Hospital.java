package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a hospital entity in the system.
 *
 * This class serves as the root entity for storing relevant information about a hospital,
 * including its name, address, geographic coordinates (latitude and longitude), and
 * associated bed availability across different medical specializations. A hospital can
 * contain multiple records of bed availability represented by {@code HospitalBedAvailability}.
 *
 * Features:
 * - Stores basic details like name, address, city, postal code, and geographic details.
 * - Maintains relationships with other entities such as {@code HospitalBedAvailability}.
 *
 * An embedded static class {@code SpecializationAvailability} is provided to represent
 * a simplified view of the medical specialization and its corresponding available beds.
 * This is particularly useful for DTO conversions and external representations.
 *
 * Annotations:
 * - The class is marked as an entity to be managed by the persistence framework.
 * - Uses project Lombok annotations to automate the generation of boilerplate code like
 *   getters, setters, constructors, and equals/hashCode methods.
 *
 * Relationships:
 * - One-to-many relationship with the {@code HospitalBedAvailability} entity to track
 *   availability of hospital beds for different medical specializations.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String postCode;

    private String city;

    private double latitude;

    private double longitude;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<HospitalBedAvailability> hospitalBedAvailabilities = new HashSet<>();

    /**
     * Represents the availability of a specific medical specialization within a hospital.
     *
     * The {@code SpecializationAvailability} class encapsulates the details of a medical
     * specialization and the number of beds available for that specialization. It is
     * typically used as a simplified view of the detailed hospital bed availability
     * data for external communication or DTO transformations.
     *
     * Attributes:
     * - id: Unique identifier of the medical specialization.
     * - name: Name of the medical specialization.
     * - bedsAvailable: Number of beds currently available for this specialization within the hospital.
     *
     * Annotations:
     * - {@code @Data}: Automatically generates getters, setters, equals, hashCode,
     *   and toString methods.
     * - {@code @NoArgsConstructor}: Generates a no-argument constructor.
     * - {@code @AllArgsConstructor}: Generates a constructor with all fields.
     *
     * Purpose:
     * - Provides a concise representation of medical specialization and its bed
     *   availability in the context of a hospital.
     * - Used in DTOs like {@code HospitalDTO} to facilitate the transfer of
     *   specialization availability data between layers of the application.
     *
     * Typical Use Cases:
     * - Incorporated in data transfer objects (DTOs) to transfer specialization
     *   and bed availability details.
     * - Used in mapping hospital bed data to a simplified view for external consumers.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecializationAvailability {
        private Long id;
        private String name;
        private int bedsAvailable;
    }
}