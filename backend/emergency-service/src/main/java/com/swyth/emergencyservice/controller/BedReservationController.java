package com.swyth.emergencyservice.controller;

import com.swyth.emergencyservice.dto.BedReservationDTO;
import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.service.BedReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/v1/bed-reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO: don't let it like that in Production env
public class BedReservationController {
    private final BedReservationService bedReservationService;

    public BedReservationController(BedReservationService bedReservationService) {
        this.bedReservationService = bedReservationService;
    }

    // TODO: Handle resource not Found
    @GetMapping("{id}")
    public ResponseEntity<BedReservationResponseDTO> getBedReservationById(@PathVariable Long id) {
        BedReservationResponseDTO bedReservation = bedReservationService.findById(id);
        return ResponseEntity.ok(bedReservation);
    }

    @PostMapping("")
    public ResponseEntity<BedReservationResponseDTO> createBedReservation(@Valid @RequestBody BedReservationDTO bedReservationRequest) {
        try {
            return bedReservationService.createBedReservation(
                    bedReservationRequest.getHospitalId(),
                    bedReservationRequest.getMedicalSpecializationId(),
                    bedReservationRequest.getReservationFirstName(),
                    bedReservationRequest.getReservationLastName(),
                    bedReservationRequest.getReservationEmail(),
                    bedReservationRequest.getReservationPhoneNumber()
            );
        } catch (BedUnavailableException exception) {
            throw new BedUnavailableException(exception.getMessage());
        }
    }
}
