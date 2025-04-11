package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.EmergencyLocationDTO;
import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.NearestHospitalDTO;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing hospital-related endpoints.
 *
 * This controller serves as the entry point for hospital-related API requests,
 * such as fetching details about all hospitals, retrieving a specific hospital by ID,
 * and finding the nearest hospital based on a given location and medical specialization.
 * It interacts with the service layer {@link HospitalService} to fetch and process hospital data.
 *
 * Endpoints:
 * - GET /v1/hospitals: Retrieves a list of all hospitals.
 * - GET /v1/hospitals/{id}: Fetches details of a specific hospital by its ID.
 * - POST /v1/hospitals/nearest: Finds the nearest hospital based on location and specialization.
 *
 * Annotations:
 * - {@code @Controller} and {@code @RestController}: Indicates this is a Spring controller for REST APIs.
 * - {@code @RequestMapping("/v1/hospitals")}: Maps all endpoints to the base path.
 */
@Controller
@RestController
@RequestMapping("/v1/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("")
    public ResponseEntity<List<HospitalDTO>> getHospitals() {
        List<HospitalDTO> hospitals = hospitalService.findAll();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) {
        try {
            HospitalDTO hospital = hospitalService.findById(id);
            return ResponseEntity.ok(hospital);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    /**
     * Finds the nearest hospital based on the given location and medical specialization.
     *
     * This method accepts an emergency location request containing latitude, longitude,
     * and a medical specialization ID, and returns information about the nearest hospital
     * that matches the criteria.
     *
     * @param emergencyLocationDTO DTO containing the required details for finding the nearest hospital:
     *                              - medicalSpecializationId: The ID of the desired medical specialization.
     *                              - latitude: The latitude of the given location.
     *                              - longitude: The longitude of the given location.
     * @return A ResponseEntity containing a NearestHospitalDTO that represents the nearest hospital
     *         matching the given criteria, including details such as the hospital's name, address,
     *         and geographic coordinates.
     */
    @PostMapping("/nearest")
    public ResponseEntity<NearestHospitalDTO> getNearestHospitals(@Valid @RequestBody EmergencyLocationDTO emergencyLocationDTO) {
        NearestHospitalDTO nearestHospital = hospitalService.findNearestHospital(
                emergencyLocationDTO.getMedicalSpecializationId(),
                emergencyLocationDTO.getLatitude(),
                emergencyLocationDTO.getLongitude()
        );
        return ResponseEntity.ok(nearestHospital);
    }

}