package com.swyth.emergencyservice.model;

import lombok.Data;

/**
 * Represents a hospital entity with relevant details such as its name, address,
 * location coordinates, and other identifying attributes.
 *
 * This class is used to model and store information about hospitals. It primarily
 * acts as a data container to keep track of a hospital's detailed attributes,
 * such as its unique identifier, geographical location, and address.
 *
 * Instances of this class can be used in various contexts, such as hospital
 * management systems, medical reservation systems, and location-based healthcare
 * services.
 */
@Data
public class Hospital {
    private Long id;
    private String name;
    private String address;
    private String postCode;
    private String city;
    private double latitude;
    private double longitude;
}
