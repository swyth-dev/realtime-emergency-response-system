package com.swyth.emergencyservice.repository;

import com.swyth.emergencyservice.entity.BedReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link BedReservation} entities.
 *
 * This interface provides CRUD operations and additional query methods for
 * accessing and manipulating the `BedReservation` data in the underlying database.
 * It extends {@link JpaRepository}, inheriting a comprehensive set of persistence and
 * query capabilities including methods for saving, updating, deleting, and retrieving
 * `BedReservation` entities.
 *
 * The repository is marked with the {@link Repository} annotation, indicating its role
 * as a data access component in the Spring Data JPA framework.
 *
 * Typical use cases include:
 * - Storing newly created bed reservations.
 * - Retrieving specific reservations by their ID or other criteria.
 * - Removing outdated or canceled reservations.
 * - Updating details of existing reservations.
 */
@Repository
public interface BedReservationRepository extends JpaRepository<BedReservation, Long> {
}
