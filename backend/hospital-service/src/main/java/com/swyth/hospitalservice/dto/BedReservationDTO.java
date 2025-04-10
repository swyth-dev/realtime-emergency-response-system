package com.swyth.hospitalservice.dto;

import lombok.Data;

/**
 * Represents a Data Transfer Object (DTO) for reserving hospital beds.
 *
 * This class encapsulates the details required for reserving a hospital bed
 * associated with a specific hospital and medical specialization. It serves
 * as an intermediary object for transferring data between layers, such as
 * between a client and service, or a service and repository.
 *
 * Attributes:
 * - id: The unique identifier for the bed reservation.
 * - hospitalId: The unique identifier of the hospital where the bed reservation is being made.
 * - medicalSpecializationId: The unique identifier of the medical specialization related to the bed reservation.
 *
 * Usage Context:
 * - This DTO is used primarily in operations that involve updating or querying
 *   hospital bed availability for specific medical specializations and hospitals.
 * - It facilitates the transfer of the required data without exposing the complete
 *   internal details of the entities involved, such as hospital or specialization objects.
 *
 * Features:
 * - Encapsulates the key details required for reserving a hospital bed.
 * - Works with service methods that update or check hospital bed availability.
 * - Simplifies service method inputs by wrapping necessary identifiers into a single object.
 */
@Data
public class BedReservationDTO {
    private Long id;
    private Long hospitalId;
    private Long medicalSpecializationId;
}
