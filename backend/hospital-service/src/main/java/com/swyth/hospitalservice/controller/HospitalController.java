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
     * POST endpoint to retrieve the nearest hospital based on latitude and longitude.
     * Rationale for POST:
     * - Sensitive data (latitude, longitude) is sent in the request body for better security.
     * - Avoids exposing data in query parameters, which can be logged or cached.
     * - Suitable for operations involving calculations, such as identifying the nearest hospital.
*    * @param locationDTO contains latitude, longitude, and specialization ID.
*    * @return nearest hospital details.
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
// TODO : Virer tout ce qui ne sert à rien en endpoint