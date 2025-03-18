package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.HospitalDtoMapper;
import com.swyth.hospitalservice.dto.NearestHospitalDTO;
import com.swyth.hospitalservice.dto.NearestHospitalDtoMapper;
import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    public HospitalService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
    }

    public List<HospitalDTO> findAll() {
        Set<Hospital> hospitals = hospitalBedAvailabilityRepository.findAll().stream()
                .collect(Collectors.groupingBy(HospitalBedAvailability::getHospital))
                .keySet();

        return HospitalDtoMapper.convertToDTO(hospitals);
    }


    public NearestHospitalDTO findNearestHospital(Long medicalSpecializationId, Double latitude, Double longitude) {
        // Fetch all hospitals with the given medical specialization
        List<HospitalBedAvailability> availabilities = hospitalBedAvailabilityRepository.findAll().stream()
                .filter(hba -> hba.getSpecialization().getId().equals(medicalSpecializationId))
                .toList();

        if (availabilities.isEmpty()) {
            return null; // No hospital found with the requested specialization.
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
            return null;
        }

        // Convert the nearest hospital to the DTO
        return NearestHospitalDtoMapper.convertToDTO(nearestHospital);
    }
}
