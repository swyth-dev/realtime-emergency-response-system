package com.swyth.emergencyservice.controller;

import com.swyth.emergencyservice.dto.BedReservationDTO;
import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.service.BedReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Rest Controller handling bed reservation-related operations.
 *
 * The BedReservationController manages endpoints for creating bed reservations
 * and retrieving information about existing reservations. It interacts with
 * the {@link BedReservationService} to perform the necessary business logic and
 * return results as HTTP responses.
 *
 * Endpoints:
 * - GET /v1/bed-reservations/{id}: Retrieves details of a bed reservation by its unique ID.
 * - POST /v1/bed-reservations: Creates a new bed reservation with the provided details.
 *
 * Dependencies:
 * - {@link BedReservationService}: The service layer responsible for bed reservation operations.
 *
 * Validation and Exceptions:
 * - Handles input validation for creating bed reservations using {@link BedReservationDTO}.
 * - Throws {@link BedUnavailableException} if no bed is available for the requested criteria.
 */
@Controller
@RestController
@RequestMapping("/v1/bed-reservations")
public class BedReservationController {
    private final BedReservationService bedReservationService;

    /**
     * Constructs the BedReservationController with a BedReservationService dependency.
     *
     * This constructor initializes the controller with the specified service, which provides
     * the business logic for handling bed reservation-related operations such as creation
     * and retrieval of bed reservations.
     *
     * @param bedReservationService the service responsible for managing bed reservation logic
     */
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
    public Mono<ResponseEntity<BedReservationResponseDTO>> createBedReservation(@Valid @RequestBody BedReservationDTO bedReservationRequest) {
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
