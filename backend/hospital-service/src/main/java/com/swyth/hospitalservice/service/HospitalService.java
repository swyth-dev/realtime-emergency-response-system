package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.*;
import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import com.swyth.hospitalservice.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository, HospitalRepository hospitalRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public List<HospitalDTO> findAll() {
        Set<Hospital> hospitals = new HashSet<>(hospitalRepository.findAll());

        if (hospitals.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals found");
        }

        return HospitalDtoMapper.convertToDTO(hospitals);
    }

    public HospitalDTO findById(Long id) {
        Hospital hospital = hospitalRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
        return HospitalDtoMapper.convertToDTO(hospital);
    }


    public NearestHospitalDTO findNearestHospital(Long medicalSpecializationId, Double latitude, Double longitude) {
        // Fetch all hospitals with the given medical specialization
        List<HospitalBedAvailability> availabilities = hospitalBedAvailabilityRepository.findAll().stream()
                .filter(hba -> hba.getSpecialization().getId().equals(medicalSpecializationId))
                .toList();

        if (availabilities.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals availability for this specialization " + medicalSpecializationId); // No hospital found with the requested specialization.
        }

        // Simple algorithm to calculate the nearest hospital based on latitude and longitude, based on 2D Euclidean Calculation
        Hospital nearestHospital = null;
        double minDistance = Double.MAX_VALUE;

        for (HospitalBedAvailability availability : availabilities) {
            Hospital hospital = availability.getHospital();
            double distance = Math.sqrt(
                    Math.pow(hospital.getLatitude() - latitude, 2) +
                            Math.pow(hospital.getLongitude() - longitude, 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearestHospital = hospital;
            }
        }

        if (nearestHospital == null) {
            throw new ResourceNotFoundException("No near hospital found");
        }

        // Convert the nearest hospital to the DTO
        return NearestHospitalDtoMapper.convertToDTO(nearestHospital);
    }
}
