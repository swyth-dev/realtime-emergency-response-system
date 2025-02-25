package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.List;
import java.util.stream.Collectors;

public class HospitalDtoMapper {

    private HospitalDtoMapper() {
    }

    public static HospitalDTO convertToDTO(Hospital hospital) {
        List<String> specializations = hospital.getSpecializations().stream()
                .map(MedicalSpecialization::getSpecializationName)
                .collect(Collectors.toList());

        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getPostCode(),
                hospital.getCity(),
                hospital.getLatitude(),
                hospital.getLongitude(),
                hospital.getAvailableBeds(),
                specializations
        );
    }

    public static List<HospitalDTO> convertToDTO(List<Hospital> hospitals) {
        return hospitals.stream().map(HospitalDtoMapper::convertToDTO).collect(Collectors.toList());
    }
}
