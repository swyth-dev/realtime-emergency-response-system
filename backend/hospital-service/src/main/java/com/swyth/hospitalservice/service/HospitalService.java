package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.*;
import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import com.swyth.hospitalservice.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing hospital-related operations.
 *
 * This service provides methods to perform various operations such as
 * retrieving all hospitals, finding hospitals by their ID, and locating
 * the nearest hospital with available beds for a specific medical specialization.
 * The service acts as a bridge between controllers and repositories, handling
 * business logic and data transformations.
 *
 * Dependencies:
 * - {@code HospitalBedAvailabilityRepository}: Manages data related to hospital bed availability.
 * - {@code HospitalRepository}: Manages CRUD and query operations for hospital entities.
 *
 * Features:
 * - Fetch all hospitals and return their details as DTOs.
 * - Retrieve hospital details by their unique*/
@Service
public class HospitalService {
    /**
     * Repository instance for managing hospital bed availability data.
     *
     * This variable holds a reference to the {@code HospitalBedAvailabilityRepository}, which
     * provides methods to interact with the database for CRUD operations and custom queries
     * related to {@code HospitalBedAvailability} entities. It is used in the service layer
     * to fetch and manage details about the availability of hospital beds for specific medical
     * specializations.
     *
     * Responsibilities:
     * - Facilitates data retrieval and persistence concerning hospital bed availability.
     * - Supports operations such as fetching all hospital bed availability entries and
     *   finding entries by specific attributes like hospital and specialization.
     */
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;
    private final HospitalRepository hospitalRepository;

    /**
     * Constructs a new HospitalService with the specified repositories.
     *
     * This constructor initializes the service with the necessary dependencies
     * to interact with hospital and hospital bed availability data. The dependencies
     * are injected via constructor injection.
     *
     * @param hospitalBedAvailabilityRepository the repository for managing hospital bed availability data
     * @param hospitalRepository the repository for managing hospital entity data
     */
    public HospitalService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository, HospitalRepository hospitalRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * Retrieves a list of all hospitals and converts them to DTOs.
     *
     * This method fetches all hospital records from the repository, ensures there are
     * no duplicates, and maps the resulting entities to data transfer objects (DTOs).
     * If no hospitals are found, a {@code ResourceNotFoundException} is thrown.
     *
     * @return a list of {@code HospitalDTO} objects representing all hospitals in the system.
     * @throws ResourceNotFoundException if no hospitals are found in the repository.
     */
    public List<HospitalDTO> findAll() {
        Set<Hospital> hospitals = new HashSet<>(hospitalRepository.findAll());

        if (hospitals.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals found");
        }

        return HospitalDtoMapper.convertToDTO(hospitals);
    }

    /**
     * Finds a hospital by its unique identifier and converts it into a DTO.
     *
     * This method retrieves a hospital entity from the repository using the provided
     * ID. If no hospital is found, a {@code ResourceNotFoundException} is thrown. The
     * retrieved hospital entity is then mapped to a {@code HospitalDTO}.
     *
     * @param id the unique identifier of the hospital to be retrieved
     * @return a {@code HospitalDTO} representing the hospital with the specified ID
     * @throws ResourceNotFoundException if no hospital is found with the given ID
     */
    public HospitalDTO findById(Long id) {
        Hospital hospital = hospitalRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
        return HospitalDtoMapper.convertToDTO(hospital);
    }


    /**
     * Finds the nearest hospital with available beds for a specific medical specialization.
     *
     * This method calculates the nearest hospital based on a 2D Euclidean distance formula using latitude and longitude
     * while considering only hospitals that have beds available for the specified medical specialization. If no hospital
     * meets the criteria, the method throws a ResourceNotFoundException.
     *
     * @param medicalSpecializationId the ID of the medical specialization for which bed availability is being searched
     * @param latitude the latitude of the location to calculate proximity
     * @param longitude the longitude of the location to calculate proximity
     * @return a DTO representing the nearest hospital with available beds for the specified specialization
     * @throws ResourceNotFoundException if no hospital with the specified specialization or within the proximity is found
     */
    public NearestHospitalDTO findNearestHospital(Long medicalSpecializationId, Double latitude, Double longitude) {
        // Fetch all hospitals with the given medical specialization
        List<HospitalBedAvailability> availabilities = hospitalBedAvailabilityRepository.findAll().stream()
                .filter(hba -> hba.getSpecialization().getId().equals(medicalSpecializationId))
                .toList();

        if (availabilities.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals availability for this specialization " + medicalSpecializationId); // No hospital found with the requested specialization.
        }

        // Simple algorithm to calculate the nearest hospital based on latitude and longitude, based on 2D Euclidean Calculation
        Hospital nearestHospital = null;
        double minDistance = Double.MAX_VALUE;

        for (HospitalBedAvailability availability : availabilities) {
            Hospital hospital = availability.getHospital();
            double distance = Math.sqrt(
                    Math.pow(hospital.getLatitude() - latitude, 2) +
                            Math.pow(hospital.getLongitude() - longitude, 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearestHospital = hospital;
            }
        }

        if (nearestHospital == null) {
            throw new ResourceNotFoundException("No near hospital found");
        }

        // Convert the nearest hospital to the DTO
        return NearestHospitalDtoMapper.convertToDTO(nearestHospital);
    }
}
