package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.HospitalBedAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing hospital bed availability data.
 *
 * This interface extends {@code JpaRepository} to provide CRUD operations
 * and custom query support for the {@code HospitalBedAvailability} entity.
 * It facilitates interaction with the database to handle data related to
 * the availability of hospital beds for specific medical specializations.
 *
 * Features:
 * - Supports default methods provided by {@code JpaRepository}, such as saving,
 *   deleting, and retrieving entities.
 * - Provides a custom query method to find bed availability by hospital
 *   and medical specialization identifiers.
 *
 * Query Methods:
 * - {@code findByHospitalIdAndSpecializationId}: Fetches an optional {@code HospitalBedAvailability}
 *   entity based on the specified hospital and medical specialization identifiers.
 */
public interface HospitalBedAvailabilityRepository extends JpaRepository<HospitalBedAvailability, HospitalBedAvailabilityId> {
    // Find an entry by hospitalId and medicalSpecializationId
    Optional<HospitalBedAvailability> findByHospitalIdAndSpecializationId(Long hospitalId, Long medicalSpecializationId);
}
