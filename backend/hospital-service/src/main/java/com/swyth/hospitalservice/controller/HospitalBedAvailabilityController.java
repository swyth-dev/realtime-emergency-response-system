package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.exception.BedUnavailableException;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.service.HospitalBedAvailabilityService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing hospital bed availability.
 *
 * This class defines endpoints for checking the availability of hospital beds for
 * a specific medical specialization in a given hospital. It interacts with the
 * {@code HospitalBedAvailabilityService} to perform the required operations.
 *
 * Features:
 * - Defines RESTful endpoint for checking bed availability.
 * - Handles required request parameters and delegates processing to the service layer.
 *
 * Endpoint Mapping:
 * - Base URL: /v1/bed-availabilities
 *
 * Functionalities:
 * - Verifies hospital bed availability for the given medical specialization and hospital ID.
 * - Throws appropriate exceptions when no beds are available.
 *
 * Annotations:
 * - {@code @Controller}: Identifies this class as a Spring Controller.
 * - {@code @ResponseBody}: Indicates that the return value of the methods will be serialized
 *   directly into the HTTP response body.
 * - {@code @RequestMapping}: Maps incoming web requests to specific controller methods.
 *
 * Constructor:
 * - Accepts a {@code HospitalBedAvailabilityService} for managing bed availability data.
 *
 * Exceptions:
 * - Throws {@code BedUnavailableException} if no beds are available for the specified
 *   inputs.
 */
@Controller
@ResponseBody
@RequestMapping("/v1/bed-availabilities")
public class HospitalBedAvailabilityController {
    private final HospitalBedAvailabilityService hospitalBedAvailabilityService;

    /**
     * Constructor for the HospitalBedAvailabilityController class.
     *
     * Initializes the controller with the provided HospitalBedAvailabilityService.
     * This service is used to handle operations related to hospital bed availability.
     *
     * @param hospitalBedAvailabilityService the service for managing hospital bed availability
     */
    public HospitalBedAvailabilityController(HospitalBedAvailabilityService hospitalBedAvailabilityService) {
        this.hospitalBedAvailabilityService = hospitalBedAvailabilityService;
    }

    /**
     * Checks the availability of hospital beds for a specified medical specialization
     * in a given hospital.
     *
     * This endpoint queries the hospital bed availability service to determine if there are
     * available beds for the provided medical specialization and hospital ID. If no beds
     * are available, a {@code BedUnavailableException} is thrown.
     *
     * @param medicalSpecializationId the unique identifier of the medical specialization
     * @param hospitalId the unique identifier of the hospital
     * @return a {@code ResponseEntity} containing a boolean value indicating whether at least
     *         one bed is available
     * @throws BedUnavailableException if no beds are available for the specified medical specialization and hospital
     */
    @PostMapping("/check")
    public ResponseEntity<Boolean> checkBedAvailability(
            @NotNull @RequestParam("medicalSpecializationId") Long medicalSpecializationId,
            @NotNull @RequestParam("hospitalId") Long hospitalId) {
        try {
            return hospitalBedAvailabilityService.checkBedAvailability(medicalSpecializationId, hospitalId);
        } catch (BedUnavailableException exception) {
            throw new BedUnavailableException(exception.getMessage());
        }

    }
}
