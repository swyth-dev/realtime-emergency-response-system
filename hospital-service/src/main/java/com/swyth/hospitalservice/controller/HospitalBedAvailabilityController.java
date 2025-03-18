package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.service.HospitalBedAvailabilityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/v1/bed-availabilities")
public class HospitalBedAvailabilityController {
    private final HospitalBedAvailabilityService hospitalBedAvailabilityService;

    public HospitalBedAvailabilityController(HospitalBedAvailabilityService hospitalBedAvailabilityService) {
        this.hospitalBedAvailabilityService = hospitalBedAvailabilityService;
    }

    @PostMapping("/check")
    public boolean checkBedAvailability(@NotNull @RequestParam Long medicalSpecializationId, @NotNull @RequestParam Long hospitalId) {
        return hospitalBedAvailabilityService.checkBedAvailability(medicalSpecializationId, hospitalId);
    }
}
