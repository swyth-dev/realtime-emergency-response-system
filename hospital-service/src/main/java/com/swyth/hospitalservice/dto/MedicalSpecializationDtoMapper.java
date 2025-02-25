package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.List;
import java.util.stream.Collectors;

public class MedicalSpecializationDtoMapper {

    private MedicalSpecializationDtoMapper() {
    }

    public static MedicalSpecializationDTO convertToDTO(MedicalSpecialization specializations) {
        List<String> hospitals = specializations.getHospitals().stream()
                .map(Hospital::getName)
                .collect(Collectors.toList());

        return new MedicalSpecializationDTO(
                specializations.getId(),
                specializations.getSpecializationName(),
                specializations.getSpecializationGroup(),
                hospitals
        );
    }

    public static List<MedicalSpecializationDTO> convertToDTO(List<MedicalSpecialization> specializations) {
        return specializations.stream().map(MedicalSpecializationDtoMapper::convertToDTO).collect(Collectors.toList());
    }
}
