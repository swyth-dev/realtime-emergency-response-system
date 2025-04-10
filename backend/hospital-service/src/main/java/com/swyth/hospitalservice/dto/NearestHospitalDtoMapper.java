package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;

/**
 * Utility class for converting a Hospital entity object to a NearestHospitalDTO object.
 *
 * This class provides a static method for transforming a Hospital entity
 * into its corresponding DTO representation, encapsulating only the
 * relevant details required for external operations like API responses.
 *
 * The mapping process transforms relevant properties such as the hospital's
 * ID, name, address, postal code, city, latitude, and longitude from the
 * provided Hospital object into a new NearestHospitalDTO instance.
 *
 * The primary use case for this class is to provide a standardized and consistent
 * way of creating DTO objects that encapsulate hospital details, particularly
 * in the context of finding or representing the nearest hospital to a location.
 */
public class NearestHospitalDtoMapper {
    private NearestHospitalDtoMapper() {
    }

    /**
     * Converts a {@link Hospital} entity into a {@link NearestHospitalDTO}.
     *
     * This method extracts the details of the given hospital and maps
     * them to the corresponding fields of a NearestHospitalDTO instance.
     *
     * @param nearestHospital the {@code Hospital} entity to be converted into a DTO
     * @return a {@link NearestHospitalDTO} representing the given hospital
     */
    public static NearestHospitalDTO convertToDTO(Hospital nearestHospital) {
        // Convert the nearest hospital to the DTO
        return new NearestHospitalDTO(
                nearestHospital.getId(),
                nearestHospital.getName(),
                nearestHospital.getAddress(),
                nearestHospital.getPostCode(),
                nearestHospital.getCity(),
                nearestHospital.getLatitude(),
                nearestHospital.getLongitude()
        );
    }
}
