package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.MedicalSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@code MedicalSpecialization} entities.
 *
 * This interface extends {@code JpaRepository}, providing basic CRUD operations,
 * along with pagination and sorting functionality for the {@code MedicalSpecialization} entity.
 *
 * Purpose:
 * - Acts as a Data Access Layer to interact with the underlying database.
 * - Facilitates retrieval, update, and persistence operations for {@code MedicalSpecialization} entities.
 *
 * Key Features:
 * - Supports retrieval of all medical specializations from the database.
 * - Enables retrieval of a specific medical specialization by its ID.
 * - Allows for saving or updating a {@code MedicalSpecialization} entity.
 * - Provides the ability to delete medical specialization records by ID or as an entity.
 *
 * Relationships:
 * - Interacts with related entities such as {@code HospitalBedAvailability} through the
 *   persistence layer when accessing or modifying associated data.
 *
 * Usage Context:
 * - Used by the {@code MedicalSpecializationService} to execute business logic
 *   related to medical specializations.
 * - Plays a crucial role in retrieving data for operations like listing all available
 *   medical specializations or fetching details of a specific specialization.
 *
 * Annotations:
 * - Marked with {@code @Repository} to indicate its role as a Spring repository.
 * - Inherits annotations and behavior from {@code JpaRepository}.
 */
@Repository
public interface MedicalSpecializationRepository extends JpaRepository<MedicalSpecialization, Long> {
}
