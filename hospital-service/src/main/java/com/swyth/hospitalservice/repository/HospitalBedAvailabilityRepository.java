package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.HospitalMedicalSpecializationsKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalBedAvailabilityRepository extends JpaRepository<HospitalBedAvailability, HospitalMedicalSpecializationsKey> {

    // Default query using method name convention
    List<HospitalBedAvailability> findByMedicalSpecializationId(Long medicalSpecializationId);

    List<HospitalBedAvailability> findByHospitalId(Long hospitalId);
}
