package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.MedicalSpecialization;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class responsible for mapping {@link MedicalSpecialization} entities
 * to their corresponding {@link MedicalSpecializationDTO} representations.
 *
 * This mapper provides static methods to facilitate the transformation of entity objects
 * into DTOs, making them suitable for use in the application's service or presentation layers.
 * It handles conversions for both single entities and collections of entities.
 *
 * Functionalities:
 * - Convert a single {@link MedicalSpecialization} entity to a {@link MedicalSpecializationDTO}.
 * - Convert a collection (set) of {@link MedicalSpecialization} entities into a list of {@link MedicalSpecializationDTO}.
 *
 * Characteristics:
 * - Ensures that hospital availability details are accurately mapped and sorted by the hospital ID
 *   for consistent and predictable results.
 * - Implements transformations in a way that adheres to immutability principles, using streams and collectors.
 *
 * Usage:
 * - This class is designed for use as a utility and therefore has a private constructor to prevent instantiation.
 * - Methods are static and intended to be called directly on the class.
 */
public class MedicalSpecializationDtoMapper {

    private MedicalSpecializationDtoMapper() {
    }

    /**
     * Converts a {@link MedicalSpecialization} entity to a {@link MedicalSpecializationDTO}.
     *
     * This method transforms a {@link MedicalSpecialization} object into its corresponding
     * Data Transfer Object (DTO), encapsulating details such as the specialization's ID,
     * name, group, and associated hospital availability information.
     *
     * @param specialization the {@link MedicalSpecialization} entity to be converted
     * @return the corresponding {@link MedicalSpecializationDTO} containing the transformed data
     */
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

    /**
     * Converts a set of {@link MedicalSpecialization} entities to a list of {@link MedicalSpecializationDTO} objects.
     *
     * This method processes a given set of {@link MedicalSpecialization}, sorting them by their IDs in ascending order
     * (with nulls appearing last), and maps each entity to its corresponding {@link MedicalSpecializationDTO}.
     *
     * @param specializations the set of {@link MedicalSpecialization} entities to be converted
     * @return a list of {@link MedicalSpecializationDTO} objects corresponding to the provided entities
     */
    public static List<MedicalSpecializationDTO> convertToDTO(Set<MedicalSpecialization> specializations) {
        return specializations.stream()
                .sorted(Comparator.comparing(MedicalSpecialization::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(MedicalSpecializationDtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
