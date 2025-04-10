package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.MedicalSpecialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing information about a medical specialization.
 *
 * This class is designed to facilitate the transfer of data between layers in the application by encapsulating
 * information about a medical specialization. It includes details about the specialization itself and
 * a list of associated hospital availability details.
 *
 * Attributes:
 * - id: The unique identifier of the medical specialization.
 * - name: The name of the medical specialization (e.g., Cardiology, Neurology).
 * - group: The category or group to which the specialization belongs.
 * - hospitals: A list of hospital availability information associated with this specialization. This includes
 *   details about hospitals, such as name, address, geographic location, and bed availability.
 *
 * Annotations:
 * - Lombok annotations are used to simplify the code:
 *      - @Data: Generates getters, setters, and other utility methods.
 *      - @NoArgsConstructor: Generates a no-argument constructor.
 *      - @AllArgsConstructor: Generates an all-argument constructor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalSpecializationDTO {
    private Long id;
    private String name;
    private String group;
    private List<MedicalSpecialization.HospitalAvailability> hospitals;
}
