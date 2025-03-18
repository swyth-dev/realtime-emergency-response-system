package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import org.springframework.stereotype.Service;

@Service
public class HospitalBedAvailabilityService {
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    public HospitalBedAvailabilityService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
    }

    // Todo : handle if hospital id or spec id does not exist
    public boolean checkBedAvailability(Long medicalSpecializationId, Long hospitalId) {
        boolean isBedAvailable = hospitalBedAvailabilityRepository.findAll().stream()
                .anyMatch(hba -> hba.getSpecialization().getId().equals(medicalSpecializationId)
                        && hba.getHospital().getId().equals(hospitalId)
                        && hba.getAvailable_beds() > 0);
        return isBedAvailable;
    }
}
