package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.HospitalBedAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalBedAvailabilityRepository extends JpaRepository<HospitalBedAvailability, HospitalBedAvailabilityId> {
    // Find an entry by hospitalId and medicalSpecializationId
    Optional<HospitalBedAvailability> findByHospitalIdAndSpecializationId(Long hospitalId, Long medicalSpecializationId);
}
