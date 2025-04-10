package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.service.MedicalSpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing medical specializations.
 *
 * This controller provides RESTful endpoints for retrieving information about
 * medical specializations. It acts as an entry point for client requests related
 * to medical specialization data. The controller delegates business logic
 * operations to the {@code MedicalSpecializationService}.
 *
 * Mappings and Responsibilities:
 * - Handles HTTP GET requests for retrieving all medical specializations.
 * - Handles HTTP GET requests for retrieving a specific medical specialization by ID.
 *
 * Exceptions:
 * - Throws {@code ResourceNotFoundException} when no specializations are found or
 *   the requested specialization ID does not exist.
 *
 * Dependencies:
 * - {@code MedicalSpecializationService}: Used to perform business logic and
 *   interact with the underlying data repository.
 *
 * Typical Use Cases:
 * - Listing all available medical specializations.
 * - Fetching details of a specific medical specialization identified by its ID.
 *
 * Annotations:
 * - {@code @Controller}: Indicates that this class serves as a web controller.
 * - {@code @RestController}: Combines {@code @Controller} and {@code @ResponseBody},
 *   providing convenient configuration for REST APIs.
 * - {@code @RequestMapping}: Maps HTTP requests to methods within this controller.
 */
@Controller
@RestController
@RequestMapping("/v1/specializations")
public class MedicalSpecializationController {
    private final MedicalSpecializationService medicalSpecializationService;

    public MedicalSpecializationController(MedicalSpecializationService medicalSpecializationService) {
        this.medicalSpecializationService = medicalSpecializationService;
    }

    /**
     * Retrieves a list of all available medical specializations.
     *
     * This method handles HTTP GET requests to fetch all medical specializations
     * from the system. It uses the {@code MedicalSpecializationService} to retrieve the data.
     * If no specializations are found, a {@code ResourceNotFoundException} is thrown.
     *
     * @return A {@code ResponseEntity} containing a list of {@code MedicalSpecializationDTO} objects,
     * representing the details of all medical specializations available in the system.
     * If no data is found, it throws a {@code ResourceNotFoundException}.
     */
    @GetMapping("")
    public ResponseEntity<List<MedicalSpecializationDTO>> getMedicalSpecializations() {
        try {
            List<MedicalSpecializationDTO> medicalSpecializations = medicalSpecializationService.findAll();
            return ResponseEntity.ok(medicalSpecializations);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicalSpecializationDTO> getMedicalSpecializationById(@PathVariable Long id) {
        try {
            MedicalSpecializationDTO medicalSpecialization = medicalSpecializationService.findById(id);
            return ResponseEntity.ok(medicalSpecialization);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
