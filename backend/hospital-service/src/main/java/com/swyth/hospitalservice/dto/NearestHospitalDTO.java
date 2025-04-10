package com.swyth.hospitalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the nearest hospital.
 * This class is used for encapsulating details of the nearest hospital such as its
 * identification, name, address, location, and geographic coordinates.
 *
 * Fields:
 * - id: Unique identifier of the hospital.
 * - name: Name of the hospital.
 * - address: Complete address of the hospital.
 * - postCode: Postal code of the hospital location.
 * - city: City where the hospital is located.
 * - latitude: Geographical latitude coordinate of the hospital.
 * - longitude: Geographical longitude coordinate of the hospital.
 *
 * Commonly used for transferring data related to the nearest hospital, especially in
 * scenarios involving hospital search functionalities.
 *
 * Includes default no-argument constructor, all-arguments constructor, as well as
 * getter and setter methods for all fields, enabled by the Lombok annotations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearestHospitalDTO {
    private Long id;
    private String name;
    private String address;
    private String postCode;
    private String city;
    private double latitude;
    private double longitude;
}
