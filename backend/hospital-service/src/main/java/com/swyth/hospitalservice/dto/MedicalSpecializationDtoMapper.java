package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MedicalSpecializationDtoMapper {

    private MedicalSpecializationDtoMapper() {
    }

    public static MedicalSpecializationDTO convertToDTO(MedicalSpecialization specialization) {
        return new MedicalSpecializationDTO(
                specialization.getId(),
                specialization.getName(),
                specialization.getGroup(),
                specialization.getHospitalBedAvailabilities().stream()
                        .map(hospitalBedAvailability -> new MedicalSpecialization.HospitalAvailability(
                                hospitalBedAvailability.getHospital().getId(),
                                hospitalBedAvailability.getHospital().getName(),
                                hospitalBedAvailability.getHospital().getAddress(),
                                hospitalBedAvailability.getHospital().getPostCode(),
                                hospitalBedAvailability.getHospital().getCity(),
                                hospitalBedAvailability.getHospital().getLatitude(),
                                hospitalBedAvailability.getHospital().getLongitude(),
                                hospitalBedAvailability.getAvailableBeds()
                        ))
                        .sorted(Comparator.comparing(MedicalSpecialization.HospitalAvailability::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList()
        );
    }

    public static List<MedicalSpecializationDTO> convertToDTO(Set<MedicalSpecialization> specializations) {
        return specializations.stream()
                .sorted(Comparator.comparing(MedicalSpecialization::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(MedicalSpecializationDtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
