package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospital_bed_availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalBedAvailability {

    @EmbeddedId
    private HospitalBedAvailabilityId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("hospitalId")
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("medicalSpecializationId")
    @JoinColumn(name = "medical_specialization_id")
    private MedicalSpecialization specialization;

    // Additional fields
    private int available_beds;
}
