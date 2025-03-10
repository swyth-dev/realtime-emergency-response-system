package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.HospitalBedAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalBedAvailabilityRepository extends JpaRepository<HospitalBedAvailability, HospitalBedAvailabilityId> {
}
