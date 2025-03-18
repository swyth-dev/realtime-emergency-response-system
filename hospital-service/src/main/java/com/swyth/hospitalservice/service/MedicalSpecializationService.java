package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import com.swyth.hospitalservice.repository.MedicalSpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicalSpecializationService {

    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;
    private final MedicalSpecializationRepository medicalSpecializationRepository;

    public MedicalSpecializationService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository, MedicalSpecializationRepository medicalSpecializationRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
        this.medicalSpecializationRepository = medicalSpecializationRepository;
    }

    public List<MedicalSpecializationDTO> findAll() {
        Set<MedicalSpecialization> specializations = hospitalBedAvailabilityRepository.findAll().stream()
                .collect(Collectors.groupingBy(HospitalBedAvailability::getSpecialization))
                .keySet();
        return MedicalSpecializationDtoMapper.convertToDTO(specializations);
    }

    public MedicalSpecializationDTO findById(Long id) {
        MedicalSpecialization specialization = medicalSpecializationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical specialization with id " + id + " not found"));
        return MedicalSpecializationDtoMapper.convertToDTO(specialization);
    }
}
