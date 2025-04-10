package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.BedReservationDTO;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.exception.BedUnavailableException;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * Service for managing hospital bed availability.
 *
 * This class provides functionalities to check and update the availability
 * of hospital beds for specific medical specializations within hospitals.
 *
 * Functional Features:
 * - Checks if a bed is available for a given hospital and medical specialization.
 * - Decrements the count of available beds when a reservation is made.
 *
 * Core Responsibilities:
 * - Accesses data through the {@code HospitalBedAvailabilityRepository}.
 * - Manages transactional integrity for operations related to hospital bed availability.
 *
 * Methods:
 * - checkBedAvailability: Validates whether a hospital has an available bed for the requested specialization.
 *   If a bed is not available, it throws a {@code BedUnavailableException}.
 * - decrementBedAvailability: A consumer method for updating the available bed count based on incoming reservations.
 *
 * Exception Handling:
 * - Throws {@code BedUnavailableException} if there are no beds available for the given inputs.
 *
 * Annotations:
 * - {@code @Service}: Identifies this class as a Spring service component.
 * - {@code @Transactional}: Ensures atomicity and consistency during database operations.
 */
@Service
public class HospitalBedAvailabilityService {
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    /**
     * Constructor for the HospitalBedAvailabilityService class.
     *
     * Initializes the service with the provided repository to manage
     * hospital bed availability data.
     *
     * @param hospitalBedAvailabilityRepository repository for accessing and managing 
     *                                          hospital bed availability information.
     */
    public HospitalBedAvailabilityService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
    }


    /**
     * Checks whether there are available beds for a specific medical specialization in a given hospital.
     *
     * This method queries the hospital bed availability repository to determine if the specified hospital
     * has any beds available for the requested medical specialization and returns the result.
     *
     * If no beds are available, a {@link BedUnavailableException} is thrown.
     *
     * @param medicalSpecializationId the unique identifier of the medical specialization
     * @param hospitalId the unique identifier of the hospital
     * @return ResponseEntity containing a boolean value indicating whether at least one bed is available
     * @throws BedUnavailableException if no beds are available for the specified medical specialization and hospital
     */
    // FIXME : handle if hospital id or spec id does not exist
    public ResponseEntity<Boolean> checkBedAvailability(Long medicalSpecializationId, Long hospitalId) {

        boolean isBedAvailable = hospitalBedAvailabilityRepository.findAll().stream()
                .anyMatch(hba -> hba.getSpecialization().getId().equals(medicalSpecializationId)
                        && hba.getHospital().getId().equals(hospitalId)
                        && hba.getAvailableBeds() > 0);

        if (!isBedAvailable) {
            throw  new BedUnavailableException("No bed is available in the hospital ID " + hospitalId + " for the given specialization ID " + medicalSpecializationId + ".");
        }

        return ResponseEntity.ok(isBedAvailable);
    }

    /**
     * Decrements the availability of hospital beds for a specific medical specialization
     * in a given hospital, based on the provided BedReservationDTO.
     *
     * This method retrieves the relevant entry from the hospital bed availability repository
     * and decrements the number of available beds. If no beds are available, a
     * BedUnavailableException is thrown. The changes are persisted to the database within
     * a transactional context.
     *
     * @return a Consumer that accepts a BedReservationDTO and updates bed availability
     *         for the specified hospital and specialization, or throws a BedUnavailableException
     *         if no beds are available.
     * @throws IllegalStateException if no entry exists for the specified hospital and specialization.
     * @throws BedUnavailableException if no available beds exist to decrement.
     */
    @Bean
    @Transactional // Ensures that all changes to the database made within the method are committed only when the method completes successfully.
    public Consumer<BedReservationDTO> decrementBedAvailability() {
        return bedReservation -> {

            Long hospitalId = bedReservation.getHospitalId();
            Long medicalSpecializationId = bedReservation.getMedicalSpecializationId();

            HospitalBedAvailability bedAvailability = hospitalBedAvailabilityRepository.findByHospitalIdAndSpecializationId(hospitalId, medicalSpecializationId)
                    .orElseThrow(() -> new IllegalStateException("No entry found for the given hospital and specialization."));

            // Check if available beds are greater than 0 before decrementing
            if (bedAvailability.getAvailableBeds() <= 0) {
                throw new BedUnavailableException("No available beds to decrement.");
            }

            // Decrement the available beds
            bedAvailability.setAvailableBeds(bedAvailability.getAvailableBeds() - 1);

            // Save the updated entity back to the database
            hospitalBedAvailabilityRepository.save(bedAvailability);
        };
    }
}
