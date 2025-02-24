package com.swyth.hospitalservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "medical_specialization", schema = "hospital_service")
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
}
