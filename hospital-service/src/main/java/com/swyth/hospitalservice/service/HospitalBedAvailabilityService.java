package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.exception.BedUnavailableException;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
