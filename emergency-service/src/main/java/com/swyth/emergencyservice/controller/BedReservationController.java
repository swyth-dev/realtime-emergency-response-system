package com.swyth.emergencyservice.controller;

import com.swyth.emergencyservice.dto.BedReservationDTO;
import com.swyth.emergencyservice.service.BedReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class BedReservationController {
    private final BedReservationService bedReservationService;

    public BedReservationController(BedReservationService bedReservationService) {
        this.bedReservationService = bedReservationService;
    }

//    TODO: Modify String type to a good one (BedReservation for example)
    @PostMapping("/bed-reservations")
    public ResponseEntity<String> createBedReservation(@Valid @RequestBody BedReservationDTO bedReservation) {
        String reservationBooked = bedReservationService.createBedReservation(
                bedReservation.getHospitalId(),
                bedReservation.getMedicalSpecializationId()
        );
        return ResponseEntity.ok(reservationBooked);
    }
}
