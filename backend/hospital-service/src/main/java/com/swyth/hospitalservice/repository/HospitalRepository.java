package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@code Hospital} entities.
 *
 * This interface provides core CRUD (Create, Read, Update, Delete) operations
 * and query execution for the {@code Hospital} entity. It extends the Spring Data
 * JPA's {@code JpaRepository}, leveraging its pre-defined methods for interacting
 * with the database.
 *
 * Responsibilities:
 * - Abstracts database interactions for the {@code Hospital} entity.
 * - Supports retrieval and persistence of {@code Hospital} objects.
 * - Enables customizable query methods using the Spring Data convention.
 *
 * Usage:
 * This interface is typically used in service classes to perform operations on the
 * hospital data without dealing with the complexities of the persistence logic.
 *
 * Annotations:
 * - {@code @Repository}: Indicates that this interface acts as a Spring Data repository,
 *   making it possible for Spring to detect and manage it as a bean.
 */
@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
