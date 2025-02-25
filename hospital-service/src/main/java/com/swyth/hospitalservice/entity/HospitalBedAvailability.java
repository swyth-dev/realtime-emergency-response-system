package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospital_bed_availability")
public class HospitalBedAvailability {

    @EmbeddedId
    private HospitalMedicalSpecializationsKey id;

    @ManyToOne
    @MapsId("hospitalId")
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @MapsId("medicalSpecializationId")
    @JoinColumn(name = "medical_specialization_id")
    private MedicalSpecialization medicalSpecialization;

    @Column(name = "available_beds")
    private int availableBeds;
}