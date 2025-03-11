package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
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
