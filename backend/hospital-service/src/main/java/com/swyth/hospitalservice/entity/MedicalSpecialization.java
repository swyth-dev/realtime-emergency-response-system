package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a medical specialization in a healthcare system.
 *
 * This entity class is used to store data about the various medical specializations
 * available in healthcare facilities and their associated information. Each
 * medical specialization is uniquely identified by its ID and categorized by its
 * name and group.
 *
 * Key Features:
 * - Each specialization has a unique ID, a name, and a group identifier for categorization.
 * - Maintains a relationship with the {@code HospitalBedAvailability} entity to represent
 *   the availability of hospital beds tied to this specialization.
 *
 * Relationships:
 * - One-to-many relationship with the {@code HospitalBedAvailability} entity, which maps
 *   the association between hospitals and this specialization. It fetches data eagerly.
 *
 * Annotations:
 * - The entity is marked with {@code @Entity}, indicating that it represents a database table.
 * - The {@code @Table} annotation specifies the name of the database table as "medical_specialization".
 * - The {@code @Id} and {@code @GeneratedValue} annotations identify the unique identifier and automatic
 *   generation strategy for the ID field.
 * - The {@code @Column} annotation is used to map the fields to their respective columns in the database,
 *   with constraints such as not-nullable rules.
 * - Lombok annotations like {@code @Data}, {@code @NoArgsConstructor}, and {@code @AllArgsConstructor} are
 *   used to automatically generate boilerplate code for getters, setters, constructors, and other methods.
 *
 * Attributes:
 * - id: The unique identifier for the specialization.
 * - name: The name of the medical specialization.
 * - group: The category or group to which the specialization belongs.
 * - hospitalBedAvailabilities: A collection representing the availability of hospital beds
 *   for this specialization in various hospitals.
 *
 * Nested Classes:
 * - {@code HospitalAvailability}: Represents a simplified view of hospital information
 *   along with details of bed availability for this specialization.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_specialization")
public class MedicalSpecialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "specialization_name")
    private String name;

    @Column(nullable = false, name = "specialization_group")
    private String group;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<HospitalBedAvailability> hospitalBedAvailabilities = new HashSet<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HospitalAvailability {
        private Long id;
        private String name;
        private String address;
        private String postCode;
        private String city;
        private double latitude;
        private double longitude;
        private int bedsAvailable;
    }
}
