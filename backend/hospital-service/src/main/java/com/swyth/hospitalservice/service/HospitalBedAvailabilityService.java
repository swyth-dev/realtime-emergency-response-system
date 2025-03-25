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

@Service
public class HospitalBedAvailabilityService {
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    public HospitalBedAvailabilityService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
    }

    // Todo : handle if hospital id or spec id does not exist
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

    @Bean
    @Transactional // Ensures that all changes to the database made within the method are committed only when the method completes successfully.
    public Consumer<BedReservationDTO> decrementBedAvailability() {
        return bedReservation -> {
            // TODO: delete this
            System.out.println("Received message: " + bedReservation.toString());

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
