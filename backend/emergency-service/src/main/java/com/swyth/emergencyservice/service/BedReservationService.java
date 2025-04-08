package com.swyth.emergencyservice.service;

import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.dto.BedReservationResponseDtoMapper;
import com.swyth.emergencyservice.entity.BedReservation;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.feign.HospitalBedAvailabilityRestClient;
import com.swyth.emergencyservice.repository.BedReservationRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class BedReservationService {
    private final BedReservationRepository bedReservationRepository;
    private final HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient;
    private final StreamBridge streamBridge;
    private final Scheduler feignScheduler;

    public BedReservationService(
            BedReservationRepository bedReservationRepository,
            HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient,
            StreamBridge streamBridge,
            @Qualifier("feignScheduler") Scheduler feignScheduler) {
        this.bedReservationRepository = bedReservationRepository;
        this.hospitalBedAvailabilityRestClient = hospitalBedAvailabilityRestClient;
        this.streamBridge = streamBridge;
        this.feignScheduler = feignScheduler;
    }

    public BedReservationResponseDTO findById(Long id) {
        BedReservation bedReservation = bedReservationRepository.findById(id).orElseThrow(() -> new IllegalStateException("No entry found for the given ID."));
        return BedReservationResponseDtoMapper.convertToDTO(bedReservation);
    }

    /**
     * Creates a new bed reservation for a specific hospital and medical specialization.
     *
     * @param hospitalId the ID of the hospital where the reservation is being made
     * @param medicalSpecializationId the ID of the medical specialization required for the reservation
     * @param reservationFirstName the first name of the person for whom the reservation is being made
     * @param reservationLastName the last name of the person for whom the reservation is being made
     * @param reservationEmail the email address of the person for whom the reservation is being made
     * @param reservationPhoneNumber the phone number of the person for whom the reservation is being made
     * @return a {@link ResponseEntity} containing the created bed reservation details wrapped in a {@link BedReservationResponseDTO}
     * @throws BedUnavailableException if no bed is available for the specified hospital and medical specialization
     */
    public Mono<ResponseEntity<BedReservationResponseDTO>> createBedReservation(
            Long hospitalId, Long medicalSpecializationId, String reservationFirstName,
            String reservationLastName, String reservationEmail, String reservationPhoneNumber) {

        // Exécuter tout le processus bloquant dans un thread séparé
        return Mono.fromCallable(() -> {
            try {
                // Check bed availability via external API
                Boolean isBedAvailable = hospitalBedAvailabilityRestClient.checkHospitalBedAvailability(
                        medicalSpecializationId, hospitalId);

                if (!isBedAvailable) {
                    throw new BedUnavailableException("No bed is available in the hospital ID " + hospitalId +
                            " for the given specialization ID " + medicalSpecializationId + ".");
                }

                // Proceed to create a bed reservation
                BedReservation reservation = new BedReservation(hospitalId, medicalSpecializationId,
                        reservationFirstName, reservationLastName, reservationEmail, reservationPhoneNumber);

                // Save the reservation & Send the message through a Data Binder for Event-Driven Architecture
                BedReservation bedReservationBooked = bedReservationRepository.save(reservation);
                streamBridge.send("bed-reservation-booked", BedReservationResponseDtoMapper.convertToDTO(bedReservationBooked));

                return ResponseEntity.ok(BedReservationResponseDtoMapper.convertToDTO(reservation));
            } catch (FeignException.NotFound exception) {
                throw new BedUnavailableException("No bed is available in the hospital ID " + hospitalId +
                        " for the given specialization ID " + medicalSpecializationId + ".");
            }
        }).subscribeOn(feignScheduler);
    }
}
