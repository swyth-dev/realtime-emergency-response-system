package com.swyth.emergencyservice.entity;

import com.swyth.emergencyservice.model.Hospital;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a bed reservation for a specific hospital and medical specialization.
 * This class is used to store and manage information about reservations made for hospital beds.
 *
 * An instance of `BedReservation` contains the necessary information about
 * the reservation, including personal details of the individual making the reservation
 * and the hospital/medical specialization relating to the reservation.
 *
 * The class includes fields to store the reservation's details and two transient fields
 * to optionally hold instances of {@code Hospital} and {@code MedicalSpecialization}.
 *
 * The class is marked as a JPA entity and includes annotations for
 * automatic generation of boilerplate code such as getters, setters, and constructors.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedReservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hospitalId;

    private Long medicalSpecializationId;

    @Transient private Hospital hospital;

    @Transient private MedicalSpecialization specialization;

    @NotNull
    private String reservationFirstName;

    @NotNull
    private String reservationLastName;

    @NotNull
    @Email
    private String reservationEmail;

    @NotNull
    @Pattern(regexp = "\\+?[1-9]\\d{1,14}", message = "Invalid phone number format") // E.164 International Format pattern
    private String reservationPhoneNumber;

    /**
     * Constructs a new instance of BedReservation with the specified parameters.
     *
     * @param hospitalId the unique identifier of the hospital where the reservation is being made
     * @param medicalSpecializationId the unique identifier of the medical specialization for which the bed reservation is requested
     * @param reservationFirstName the first name of the individual for whom the bed reservation is being made
     * @param reservationLastName the last name of the individual for whom the bed reservation is being made
     * @param reservationEmail the email address of the individual for whom the bed reservation is being made
     * @param reservationPhoneNumber the phone number of the individual for whom the bed reservation is being made
     */
    public BedReservation(Long hospitalId, Long medicalSpecializationId, String reservationFirstName, String reservationLastName, String reservationEmail, String reservationPhoneNumber) {
        this.hospitalId = hospitalId;
        this.medicalSpecializationId = medicalSpecializationId;
        this.reservationFirstName = reservationFirstName;
        this.reservationLastName = reservationLastName;
        this.reservationEmail = reservationEmail;
        this.reservationPhoneNumber = reservationPhoneNumber;
    }
}
