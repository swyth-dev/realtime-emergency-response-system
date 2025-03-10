package com.swyth.hospitalservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long id;

    @Column(nullable = false, name = "specialization_name")
    @JsonProperty("specialization_name")
    private String specializationName;

    @Column(nullable = false, name = "specialization_group")
    @JsonProperty("specialization_group")
    private String specializationGroup;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<HospitalBedAvailability> hospitalBedAvailabilities = new HashSet<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HospitalAvailability {
        private Long hospitalId;
        private String hospitalName;
        private int bedsAvailable;
    }
}
