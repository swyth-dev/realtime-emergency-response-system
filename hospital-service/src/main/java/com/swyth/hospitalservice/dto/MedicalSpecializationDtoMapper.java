package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MedicalSpecializationDtoMapper {

    private MedicalSpecializationDtoMapper() {
    }

    public static MedicalSpecializationDTO convertToDTO(MedicalSpecialization specialization) {
        return new MedicalSpecializationDTO(
                specialization.getId(),
                specialization.getSpecializationName(),
                specialization.getSpecializationGroup(),
                specialization.getHospitalBedAvailabilities().stream()
                        .map(hospitalBedAvailability -> new MedicalSpecialization.HospitalAvailability(
                                hospitalBedAvailability.getHospital().getId(),
                                hospitalBedAvailability.getHospital().getName(),
                                hospitalBedAvailability.getAvailable_beds()
                        ))
                        .toList()
        );
    }

    public static List<MedicalSpecializationDTO> convertToDTO(Set<MedicalSpecialization> specializations) {
        return specializations.stream()
                .map(MedicalSpecializationDtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
