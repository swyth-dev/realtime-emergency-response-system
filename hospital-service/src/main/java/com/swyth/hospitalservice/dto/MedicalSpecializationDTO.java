package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.MedicalSpecialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalSpecializationDTO {
    private Long id;
    private String specializationName;
    private String specializationGroup;
    private List<MedicalSpecialization.HospitalAvailability> hospitals;
}
