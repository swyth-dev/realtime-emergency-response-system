package com.swyth.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String postCode;

    private String city;

    private double latitude;

    private double longitude;

    @ManyToMany
    private ArrayList<MedicalSpecialization> medicalSpecializations;

    private int availableBeds;
}