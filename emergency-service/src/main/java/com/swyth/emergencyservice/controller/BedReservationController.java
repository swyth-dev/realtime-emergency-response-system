package com.swyth.emergencyservice.controller;

import com.swyth.emergencyservice.dto.BedReservationDTO;
import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.entity.BedReservation;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.service.BedReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/v1/bed-reservations")
public class BedReservationController {
    private final BedReservationService bedReservationService;

    public BedReservationController(BedReservationService bedReservationService) {
        this.bedReservationService = bedReservationService;
    }

//    TODO: Modify String type to a good one (BedReservation for example)
    @PostMapping("")
    public ResponseEntity<BedReservationResponseDTO> createBedReservation(@Valid @RequestBody BedReservationDTO bedReservationRequest) {
        try {
            return bedReservationService.createBedReservation(
                    bedReservationRequest.getHospitalId(),
                    bedReservationRequest.getMedicalSpecializationId()
            );
        } catch (BedUnavailableException exception) {
            throw new BedUnavailableException(exception.getMessage());
        }
    }
}
