package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing hospital information.
 *
 * This class is used to encapsulate and transfer hospital data between layers of the
 * application, typically from the service layer to the presentation layer. The DTO includes
 * details about the hospital such as its name, location, and available specializations with
 * their corresponding bed counts.
 *
 * Attributes:
 * - id: Unique identifier for the hospital.
 * - name: Name of the hospital.
 * - address: Address of the hospital.
 * - postCode: Postal code of the hospital's location.
 * - city: City where the hospital is located.
 * - latitude: Geographical latitude coordinate of the hospital.
 * - longitude: Geographical longitude coordinate of the hospital.
 * - specializations: List of medical specializations available in the hospital along with
 *   the number of beds available for each specialization.
 *
 * Annotations:
 * - The class is annotated with Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
 *   to auto-generate getters, setters, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    private Long id;
    private String name;
    private String address;
    private String postCode;
    private String city;
    private double latitude;
    private double longitude;
    private List<Hospital.SpecializationAvailability> specializations;
}
