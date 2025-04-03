package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.MedicalSpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalSpecializationServiceTest {

    @Mock
    private MedicalSpecializationRepository medicalSpecializationRepository;

    @InjectMocks
    private MedicalSpecializationService medicalSpecializationService;

    @BeforeEach
    void setUp() {
        // Initialize mocks (required to set up @Mock and @InjectMocks)
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_ReturnsSpecializations_WhenDataExists() {
        // Arrange: Create mock entities and configure repository behavior
        MedicalSpecialization specialization1 = new MedicalSpecialization(); // Default constructor
        specialization1.setId(1L); // Manually set id
        specialization1.setName("Cardiology"); // Manually set name

        MedicalSpecialization specialization2 = new MedicalSpecialization();
        specialization2.setId(2L);
        specialization2.setName("Neurology");

        Set<MedicalSpecialization> specializations = new HashSet<>(List.of(specialization1, specialization2));

        // Tell the mock repository to return these entities
        when(medicalSpecializationRepository.findAll()).thenReturn(List.of(specialization1, specialization2));

        // Convert entities to DTOs using the mapper
        List<MedicalSpecializationDTO> expectedDtos = MedicalSpecializationDtoMapper.convertToDTO(specializations);

        // Act: Call the service method
        List<MedicalSpecializationDTO> actualDtos = medicalSpecializationService.findAll();

        // Assert: Validate the service result
        assertEquals(expectedDtos, actualDtos); // Compare expected vs actual DTOs
        verify(medicalSpecializationRepository, times(1)).findAll(); // Ensure repository was called once
    }

    @Test
    void testFindAll_ThrowsResourceNotFoundException_WhenNoDataExists() {
        // Arrange: Mock the repository to return an empty list
        when(medicalSpecializationRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert: Check that the exception is thrown
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> medicalSpecializationService.findAll()
        );

        assertEquals("No medical specializations found", exception.getMessage());
        verify(medicalSpecializationRepository, times(1)).findAll(); // Ensure repository was called once
    }

    @Test
    void testFindById_ReturnsSpecializationDTO_WhenIdExists() {
        // Arrange: Create a dummy entity and set properties
        Long id = 1L;
        MedicalSpecialization specialization = new MedicalSpecialization();
        specialization.setId(id);
        specialization.setName("Cardiology");

        // Mock repository behavior
        when(medicalSpecializationRepository.findById(id)).thenReturn(Optional.of(specialization));

        // Convert the entity to a DTO using the mapper
        MedicalSpecializationDTO expectedDto = MedicalSpecializationDtoMapper.convertToDTO(specialization);

        // Act: Call the service method
        MedicalSpecializationDTO actualDto = medicalSpecializationService.findById(id);

        // Assert: Validate result and repository behavior
        assertEquals(expectedDto, actualDto); // Compare expected and actual DTO
        verify(medicalSpecializationRepository, times(1)).findById(id); // Ensure repository was called once
    }

    @Test
    void testFindById_ThrowsResourceNotFoundException_WhenIdDoesNotExist() {
        // Arrange: Mock repository to return an empty optional
        Long id = 99L;
        when(medicalSpecializationRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert: Check exception is thrown
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> medicalSpecializationService.findById(id)
        );

        // Check exception message
        assertEquals("Medical Specialization not found for id: " + id, exception.getMessage());

        // Verify repository is called once
        verify(medicalSpecializationRepository, times(1)).findById(id);
    }
}