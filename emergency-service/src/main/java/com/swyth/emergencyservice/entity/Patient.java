package com.swyth.emergencyservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    private long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    @OneToMany
    private ArrayList<BedReservation> bedReservation;
}
