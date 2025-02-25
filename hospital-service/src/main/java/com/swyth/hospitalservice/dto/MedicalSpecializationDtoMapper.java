package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalSpecializationDtoMapper {

    private MedicalSpecializationDtoMapper() {
    }

    public static MedicalSpecializationDTO convertToDTO(MedicalSpecialization specializations) {
        // Create a shallow copy of the hospitals set to avoid concurrent modification
        List<String> hospitals = new ArrayList<>(specializations.getHospitals().size());
        specializations.getHospitals().forEach(hba -> hospitals.add(hba.getHospital().getName()));

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
