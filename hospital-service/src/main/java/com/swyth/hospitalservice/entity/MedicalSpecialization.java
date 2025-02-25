package com.swyth.hospitalservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @ManyToMany(mappedBy = "specializations", fetch = FetchType.EAGER) // Referencing Student's ManyToMany field
    private Set<Hospital> hospitals = new HashSet<>();
    // TODO: check https://www.baeldung.com/jpa-many-to-many to add an additional field to relation jointure table
}
