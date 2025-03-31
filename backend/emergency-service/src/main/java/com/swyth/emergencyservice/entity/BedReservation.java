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

    // Constructor to initialize all fields
    public BedReservation(Long hospitalId, Long medicalSpecializationId, String reservationFirstName, String reservationLastName, String reservationEmail, String reservationPhoneNumber) {
        this.hospitalId = hospitalId;
        this.medicalSpecializationId = medicalSpecializationId;
        this.reservationFirstName = reservationFirstName;
        this.reservationLastName = reservationLastName;
        this.reservationEmail = reservationEmail;
        this.reservationPhoneNumber = reservationPhoneNumber;
    }
}
