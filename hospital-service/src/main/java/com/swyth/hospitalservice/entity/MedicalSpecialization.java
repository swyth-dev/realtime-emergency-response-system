package com.swyth.hospitalservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "medical_specialization")
public class MedicalSpecialization {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(nullable = false, name = "specialization_name")
    @JsonProperty("specialization_name")
    private String specializationName;

    @Column(nullable = false, name = "specialization_group")
    @JsonProperty("specialization_group")
    private String specializationGroup;

    @OneToMany(mappedBy = "medicalSpecialization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<HospitalBedAvailability> hospitals;
    // TODO: check https://www.baeldung.com/jpa-many-to-many to add an additional field to relation jointure table

}
