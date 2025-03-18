package com.swyth.emergencyservice.entity;

import com.swyth.emergencyservice.model.Hospital;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedReservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hospitalId;

    private Long medicalSpecializationId;

    @Transient private Hospital hospital;

    @Transient private MedicalSpecialization specialization;

    // Constructor to initialize all fields
    public BedReservation(Long hospitalId, Long medicalSpecializationId) {
        this.hospitalId = hospitalId;
        this.medicalSpecializationId = medicalSpecializationId;
    }
}
