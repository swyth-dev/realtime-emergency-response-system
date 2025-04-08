package com.swyth.emergencyservice.service;

import com.swyth.emergencyservice.dto.BedReservationResponseDTO;
import com.swyth.emergencyservice.entity.BedReservation;
import com.swyth.emergencyservice.exception.BedUnavailableException;
import com.swyth.emergencyservice.feign.HospitalBedAvailabilityRestClient;
import com.swyth.emergencyservice.repository.BedReservationRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BedReservationServiceTest {

    @Mock
    private BedReservationRepository bedReservationRepository;

    @Mock
    private HospitalBedAvailabilityRestClient hospitalBedAvailabilityRestClient;

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private BedReservationService bedReservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Création manuelle du service avec un scheduler de test
        bedReservationService = new BedReservationService(
                bedReservationRepository,
                hospitalBedAvailabilityRestClient,
                streamBridge,
                Schedulers.immediate() // Utilises un scheduler immédiat pour les tests
        );
    }

    @Test
    @DisplayName("Should throw IllegalStateException for invalid ID")
    void testFindByIdThrowsExceptionForInvalidId() {
        // Arrange
        Long invalidId = 999L;

        // Mock repository behavior
        Mockito.when(bedReservationRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bedReservationService.findById(invalidId));
        assertEquals("No entry found for the given ID.", exception.getMessage());
        Mockito.verify(bedReservationRepository).findById(invalidId);
    }

    @Test
    @DisplayName("Test reservation should be successfully created when bed is available")
        // Title for this test
    void testCreateBedReservationSuccess() {
        // Arrange
        Long hospitalId = 1L;
        Long medicalSpecializationId = 101L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String phoneNumber = "1234567890";

        // Mock dependencies
        when(hospitalBedAvailabilityRestClient.checkHospitalBedAvailability(medicalSpecializationId, hospitalId)).thenReturn(true);

        // Mock for repository: Simulate saving reservation
        BedReservation mockedReservation = new BedReservation(hospitalId, medicalSpecializationId, firstName, lastName, email, phoneNumber);
        when(bedReservationRepository.save(any(BedReservation.class))).thenReturn(mockedReservation);

        // Act
        ResponseEntity<BedReservationResponseDTO> response = bedReservationService.createBedReservation(hospitalId, medicalSpecializationId, firstName, lastName, email, phoneNumber).block();

        // Assert
        assert response != null;
        assertNotNull(response.getBody(), "The response body should not be null.");
        assertEquals(firstName, response.getBody().getReservationFirstName(), "The reservation first name should match.");
        assertEquals(lastName, response.getBody().getReservationLastName(), "The reservation last name should match.");
        assertEquals(email, response.getBody().getReservationEmail(), "The reservation email should match.");
        assertEquals(phoneNumber, response.getBody().getReservationPhoneNumber(), "The reservation phone number should match.");

        // Verify interactions
        verify(hospitalBedAvailabilityRestClient, times(1)).checkHospitalBedAvailability(medicalSpecializationId, hospitalId);
        verify(bedReservationRepository, times(1)).save(any(BedReservation.class));
        verify(streamBridge, times(1)).send(eq("bed-reservation-booked"), any(BedReservationResponseDTO.class));
    }

    @Test
    @DisplayName("Test exception is thrown when no bed is available")
    void testCreateBedReservationFailure_BedUnavailable() {
        // Arrange
        Long hospitalId = 1L;
        Long medicalSpecializationId = 101L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String phoneNumber = "1234567890";

        // Mock dependencies
        when(hospitalBedAvailabilityRestClient.checkHospitalBedAvailability(medicalSpecializationId, hospitalId)).thenReturn(false);

        // Act & Assert
        BedUnavailableException exception = assertThrows(BedUnavailableException.class, () -> {
            bedReservationService.createBedReservation(hospitalId, medicalSpecializationId, firstName, lastName, email, phoneNumber).block();
        });

        assertEquals("No bed is available in the hospital ID 1 for the given specialization ID 101.", exception.getMessage());

        // Verify interactions
        verify(hospitalBedAvailabilityRestClient, times(1)).checkHospitalBedAvailability(medicalSpecializationId, hospitalId);
        verifyNoInteractions(bedReservationRepository);
        verifyNoInteractions(streamBridge);
    }

    @Test
    @DisplayName("Test exception is thrown when external client returns not found")
    void testCreateBedReservationFailure_FeignClientThrowsNotFound() {
        // Arrange
        Long hospitalId = 2L;
        Long medicalSpecializationId = 202L;
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String phoneNumber = "9876543210";

        // Mock dependencies
        when(hospitalBedAvailabilityRestClient.checkHospitalBedAvailability(medicalSpecializationId, hospitalId)).thenThrow(FeignException.NotFound.class);

        // Act & Assert
        BedUnavailableException exception = assertThrows(BedUnavailableException.class, () -> {
            bedReservationService.createBedReservation(hospitalId, medicalSpecializationId, firstName, lastName, email, phoneNumber).block();
        });

        assertEquals("No bed is available in the hospital ID 2 for the given specialization ID 202.", exception.getMessage());

        // Verify interactions
        verify(hospitalBedAvailabilityRestClient, times(1)).checkHospitalBedAvailability(medicalSpecializationId, hospitalId);
        verifyNoInteractions(bedReservationRepository);
        verifyNoInteractions(streamBridge);
    }
}
