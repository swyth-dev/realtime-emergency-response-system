package com.swyth.emergencyservice.model;

import lombok.Data;

import java.util.Collection;

/**
 * Represents a specific medical specialization, such as Cardiology, Neurology, or Pediatrics.
 * This class is used to encapsulate information about a particular specialization,
 * grouping common medical practices and facilitating the organization of healthcare services.
 *
 * Instances of this class are used to identify and manage various medical specializations.
 * They are also associated with the availability of these specializations in hospitals.
 *
 * Fields of this class include:
 * - An identifier for the specialization.
 * - A name that describes the specialization.
 * - A group to categorize the specialization.
 * - A collection of hospital specialization availability references, which provide
 *   details about the availability of this specialization in different hospitals.
 */
@Data
public class MedicalSpecialization {
    private Long id;
    private String name;
    private String group;
    private Collection<HospitalMedicalSpecializationAvailability> hospitals;
}
