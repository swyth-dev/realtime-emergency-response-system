package com.swyth.emergencyservice.service;

import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.dto.BedReservationResponseDtoMapper;
import com.swyth.emergencyservice.entity.BedReservation;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.feign.HospitalBedAvailabilityRestClient;
import com.swyth.emergencyservice.repository.BedReservationRepository;
import feign.FeignException;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BedReservationService {
    private final BedReservationRepository bedReservationRepository;
    private final HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient;
    private final StreamBridge streamBridge;

    public BedReservationService(BedReservationRepository bedReservationRepository, HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient, StreamBridge streamBridge) {
        this.bedReservationRepository = bedReservationRepository;
        this.hospitalBedAvailabilityRestClient = hospitalBedAvailabilityRestClient;
        this.streamBridge = streamBridge;
    }

    public BedReservationResponseDTO findById(Long id) {
        BedReservation bedReservation = bedReservationRepository.findById(id).orElseThrow(() -> new IllegalStateException("No entry found for the given ID."));
        return BedReservationResponseDtoMapper.convertToDTO(bedReservation);
    }

    public ResponseEntity<BedReservationResponseDTO> createBedReservation(Long hospitalId, Long medicalSpecializationId, String reservationFirstName, String reservationLastName, String reservationEmail ,String reservationPhoneNumber) {

        try {
            // Check bed availability via external API
            Boolean isBedAvailable = hospitalBedAvailabilityRestClient.checkHospitalBedAvailability(medicalSpecializationId, hospitalId);

            if (!isBedAvailable) {
                throw new BedUnavailableException("No bed is available in the hospital ID " + hospitalId + " for the given specialization ID " + medicalSpecializationId + ".");
            }

            // Proceed to create a bed reservation
            BedReservation reservation = new BedReservation(hospitalId, medicalSpecializationId, reservationFirstName, reservationLastName, reservationEmail, reservationPhoneNumber);

            // Save the reservation & Send the message through a Data Binder for Event-Driven Architecture
            BedReservation bedReservationBooked = bedReservationRepository.save(reservation);
            streamBridge.send("bed-reservation-booked", BedReservationResponseDtoMapper.convertToDTO(bedReservationBooked));

            return ResponseEntity.ok(BedReservationResponseDtoMapper.convertToDTO(reservation));
        } catch (FeignException.NotFound exception) {
            throw new BedUnavailableException("No bed is available in the hospital ID " + hospitalId + " for the given specialization ID " + medicalSpecializationId + ".");
        }
    }
}
