package com.swyth.emergencyservice.model;

import lombok.Data;

import java.util.Collection;

@Data
public class MedicalSpecialization {
    private Long id;
    private String name;
    private String group;
    private Collection<HospitalMedicalSpecializationAvailability> hospitals;
}
