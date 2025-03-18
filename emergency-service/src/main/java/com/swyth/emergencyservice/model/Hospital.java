package com.swyth.emergencyservice.model;

import lombok.Data;

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
