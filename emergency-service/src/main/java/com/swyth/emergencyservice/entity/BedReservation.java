package com.swyth.emergencyservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedReservation {
    @Id
    private Long id;

    private Long hospitalId;

    private Long medicalSpecializationId;

    @ManyToOne
    private Patient patient;
}
