package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HospitalDtoMapper {

    private HospitalDtoMapper() {
    }

    public static HospitalDTO convertToDTO(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getPostCode(),
                hospital.getCity(),
                hospital.getLatitude(),
                hospital.getLongitude(),
                hospital.getHospitalBedAvailabilities().stream()
                        .map(hospitalBedAvailability -> new Hospital.SpecializationAvailability(
                                hospitalBedAvailability.getSpecialization().getId(),
                                hospitalBedAvailability.getSpecialization().getSpecializationName(),
                                hospitalBedAvailability.getAvailable_beds()
                        ))
                        .toList()
        );
    }

    public static List<HospitalDTO> convertToDTO(Set<Hospital> hospitals) {
        return hospitals.stream()
                .map(HospitalDtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
