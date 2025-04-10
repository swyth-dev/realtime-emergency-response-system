package com.swyth.emergencyservice.model;

import lombok.Data;

/**
 * Represents the availability of a specific medical specialization in a hospital.
 * This class encapsulates information about a hospital, including its location,
 * address, available beds, and other details related to the availability of
 * medical services.
 */
@Data
public class HospitalMedicalSpecializationAvailability {
    private Long id;
    private String name;
    private String address;
    private String postCode;
    private String city;
    private double latitude;
    private double longitude;
    private int bedsAvailable;
}
