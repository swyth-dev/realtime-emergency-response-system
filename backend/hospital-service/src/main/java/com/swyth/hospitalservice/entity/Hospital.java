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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecializationAvailability {
        private Long id;
        private String name;
        private int bedsAvailable;
    }
}