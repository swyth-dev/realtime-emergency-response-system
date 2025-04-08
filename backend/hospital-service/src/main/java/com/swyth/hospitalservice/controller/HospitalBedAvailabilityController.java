package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.exception.BedUnavailableException;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.service.HospitalBedAvailabilityService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/v1/bed-availabilities")
//@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO: don't let it like that in Production env
public class HospitalBedAvailabilityController {
    private final HospitalBedAvailabilityService hospitalBedAvailabilityService;

    public HospitalBedAvailabilityController(HospitalBedAvailabilityService hospitalBedAvailabilityService) {
        this.hospitalBedAvailabilityService = hospitalBedAvailabilityService;
    }

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
