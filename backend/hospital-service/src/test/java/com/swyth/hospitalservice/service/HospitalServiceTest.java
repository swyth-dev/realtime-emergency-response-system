package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.HospitalDtoMapper;
import com.swyth.hospitalservice.dto.NearestHospitalDTO;
import com.swyth.hospitalservice.dto.NearestHospitalDtoMapper;
import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import com.swyth.hospitalservice.repository.HospitalRepository;
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

class HospitalServiceTest {

    @Mock
    private HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_ReturnsHospitals_WhenDataExists() {
        // Arrange
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setName("General Hospital");
        hospital1.setLatitude(10.0);
        hospital1.setLongitude(20.0);

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setName("City Hospital");
        hospital2.setLatitude(15.0);
        hospital2.setLongitude(25.0);

        Set<Hospital> hospitals = new HashSet<>(List.of(hospital1, hospital2));

        when(hospitalRepository.findAll()).thenReturn(List.of(hospital1, hospital2));

        List<HospitalDTO> expectedDtos = HospitalDtoMapper.convertToDTO(hospitals);

        // Act
        List<HospitalDTO> actualDtos = hospitalService.findAll();

        // Assert
        assertEquals(expectedDtos, actualDtos);
        verify(hospitalRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_ThrowsResourceNotFoundException_WhenNoDataExists() {
        // Arrange
        when(hospitalRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> hospitalService.findAll()
        );

        assertEquals("No hospitals found", exception.getMessage());
        verify(hospitalRepository, times(1)).findAll();
    }

    @Test
    void testFindById_ReturnsHospitalDTO_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Hospital hospital = new Hospital();
        hospital.setId(id);
        hospital.setName("General Hospital");
        hospital.setLatitude(10.0);
        hospital.setLongitude(20.0);

        when(hospitalRepository.findById(id)).thenReturn(Optional.of(hospital));

        HospitalDTO expectedDto = HospitalDtoMapper.convertToDTO(hospital);

        // Act
        HospitalDTO actualDto = hospitalService.findById(id);

        // Assert
        assertEquals(expectedDto, actualDto);
        verify(hospitalRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_ThrowsResourceNotFoundException_WhenIdDoesNotExist() {
        // Arrange
        Long id = 99L;
        when(hospitalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> hospitalService.findById(id)
        );

        assertEquals("Hospital not found with id: " + id, exception.getMessage());
        verify(hospitalRepository, times(1)).findById(id);
    }

    @Test
    void testFindNearestHospital_ReturnsNearestHospitalDTO_WhenDataExists() {
        // Arrange: Mock hospitals and medical specializations
        Long specializationId = 1L;
        Double latitude = 10.0;
        Double longitude = 20.0;

        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setName("General Hospital");
        hospital1.setLatitude(10.0);
        hospital1.setLongitude(20.0);

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setName("City Hospital");
        hospital2.setLatitude(30.0);
        hospital2.setLongitude(40.0);

        MedicalSpecialization specialization = new MedicalSpecialization();
        specialization.setId(specializationId);
        specialization.setName("Cardiology");

        HospitalBedAvailability availability1 = new HospitalBedAvailability();
        availability1.setHospital(hospital1);
        availability1.setSpecialization(specialization);

        HospitalBedAvailability availability2 = new HospitalBedAvailability();
        availability2.setHospital(hospital2);
        availability2.setSpecialization(specialization);

        List<HospitalBedAvailability> availabilities = List.of(availability1, availability2);

        when(hospitalBedAvailabilityRepository.findAll()).thenReturn(availabilities);

        NearestHospitalDTO expectedDto = NearestHospitalDtoMapper.convertToDTO(hospital1);

        // Act
        NearestHospitalDTO actualDto = hospitalService.findNearestHospital(specializationId, latitude, longitude);

        // Assert
        assertEquals(expectedDto, actualDto);
        verify(hospitalBedAvailabilityRepository, times(1)).findAll();
    }

    @Test
    void testFindNearestHospital_ThrowsResourceNotFoundException_WhenNoHospitalsAvailable() {
        // Arrange
        Long specializationId = 1L;
        when(hospitalBedAvailabilityRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> hospitalService.findNearestHospital(specializationId, 10.0, 20.0)
        );

        assertEquals("No hospitals availability for this specialization " + specializationId, exception.getMessage());
        verify(hospitalBedAvailabilityRepository, times(1)).findAll();
    }
}