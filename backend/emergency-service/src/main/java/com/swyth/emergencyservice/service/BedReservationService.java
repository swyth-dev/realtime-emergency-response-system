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

/**
 * Service class responsible for managing bed reservation operations.
 *
 * This class provides methods to handle the reservation process for hospital beds,
 * including finding existing reservations and creating new ones. It interacts with
 * multiple components such as repositories, external APIs, and messaging streams to
 * perform its operations.
 *
 * Dependencies:
 * - {@link BedReservationRepository}: Handles persistence and data retrieval for bed reservations.
 * - {@link HospitalBedAvailabilityRestClient}: Communicates with an external service to check the availability of beds.
 * - {@link StreamBridge}: Facilitates messaging for event-driven functionality.
 * - {@link Scheduler}: Executes blocking operations in a non-blocking asynchronous flow.
 *
 * Features:
 * - Retrieve a bed reservation by its unique identifier.
 * - Create a new bed reservation after verifying bed availability through an external API.
 * - Publish reservation events using the messaging layer for downstream processing.
 *
 * Potential exceptions:
 * - {@link IllegalStateException} when a requested reservation is not found during lookup.
 * - {@link BedUnavailableException} when there are no beds available for a given hospital and specialization.
 */
@Service
public class BedReservationService {
    private final BedReservationRepository bedReservationRepository;
    private final HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient;
    private final StreamBridge streamBridge;
    private final Scheduler feignScheduler;

    /**
     * Constructs an instance of the BedReservationService.
     *
     * This constructor initializes the service with the necessary dependencies such as
     * a repository for managing bed reservations, a REST client for checking hospital bed availability,
     * a stream bridge for sending event messages, and a scheduler for controlling thread execution.
     *
     * @param bedReservationRepository the repository used for managing {@link BedReservation} entities
     * @param hospitalBedAvailabilityRestClient the REST client for interacting with the hospital-service API to check bed availability
     * @param streamBridge the event-driven messaging bridge used to send events
     * @param feignScheduler the scheduler used for executing Feign client operations
     */
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


    /**
     * Retrieves a bed reservation by its unique ID.
     *
     * This method fetches a {@link BedReservation} entity from the repository,
     * converts it into a {@link BedReservationResponseDTO}, and returns it.
     * If no reservation is found for the given ID, an {@link IllegalStateException}
     * is thrown.
     *
     * @param id the unique identifier of the bed reservation to be retrieved
     * @return a {@link BedReservationResponseDTO} object containing the details of the bed reservation
     * @throws IllegalStateException if no entry is found for the provided ID
     */
    public BedReservationResponseDTO findById(Long id) {
        BedReservation bedReservation = bedReservationRepository.findById(id).orElseThrow(() -> new IllegalStateException("No entry found for the given ID."));
        return BedReservationResponseDtoMapper.convertToDTO(bedReservation);
    }

    /**
     * Creates a new bed reservation in a specified hospital for the given medical specialization.
     *
     * This method checks the availability of beds in the specified hospital and medical specialization
     * through an external API. If a bed is available, it proceeds to create a reservation and saves the
     * reservation details. Additionally, an event message is sent for further processing in an event-driven system.
     *
     * If no beds are available or the external API returns a not-found response, a {@code BedUnavailableException}
     * is thrown.
     *
     * @param hospitalId the unique identifier of the hospital where the reservation is being made
     * @param medicalSpecializationId the unique identifier of the medical specialization for which the bed reservation is requested
     * @param reservationFirstName the first name of the individual for whom the bed reservation is being made
     * @param reservationLastName the last name of the individual for whom the bed reservation is being made
     * @param reservationEmail the email address of the individual for whom the bed reservation is being made
     * @param reservationPhoneNumber the phone number of the individual for whom the bed reservation is being made
     * @return a {@code Mono<ResponseEntity<BedReservationResponseDTO>>} containing the details of the bed reservation
     *         if successfully completed
     * @throws BedUnavailableException if no beds are available for the given hospital and medical specialization
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
