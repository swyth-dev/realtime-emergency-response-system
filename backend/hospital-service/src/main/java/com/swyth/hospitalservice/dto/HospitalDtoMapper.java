package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for mapping Hospital entities to their corresponding HospitalDTO representations.
 *
 * This class provides static methods for converting Hospital entities and collections of Hospital
 * entities to data transfer objects (DTOs), making it easier to transfer data between application
 * layers while decoupling the internal entity model from external representations.
 *
 * Features:
 * - Converts a single Hospital entity to a HospitalDTO.
 * - Converts a collection of Hospital entities (Set) to a list of HospitalDTOs, sorted by hospital ID.
 *
 * Methods:
 * - The conversion process includes mapping hospital attributes (ID, name, address, etc.) and
 *   available bed information for each medical specialization into the DTO.
 * - Ensures the output DTOs are sorted by unique identifiers for consistency in representation.
 *
 * Annotations:
 * - The class is utilitarian in purpose and is marked with a private constructor to prevent
 *   instantiation.
 *
 * Usage:
 * - The methods are designed to be used statically in services or business logic handling hospital
 *   data transformation.
 *
 * Purpose:
 * - Centralizes and standardizes the entity-to-DTO conversion logic, reducing code duplication
 *   and ensuring consistency across the application.
 */
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
                                hospitalBedAvailability.getSpecialization().getName(),
                                hospitalBedAvailability.getAvailableBeds()
                        ))
                        .sorted(Comparator.comparing(Hospital.SpecializationAvailability::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList()
        );
    }

    public static List<HospitalDTO> convertToDTO(Set<Hospital> hospitals) {
        return hospitals.stream()
                .sorted(Comparator.comparing(Hospital::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(HospitalDtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
