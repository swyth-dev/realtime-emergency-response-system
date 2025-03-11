package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.HospitalBedAvailability;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.repository.HospitalBedAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicalSpecializationService {

    private final HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository;

    public MedicalSpecializationService(HospitalBedAvailabilityRepository hospitalBedAvailabilityRepository) {
        this.hospitalBedAvailabilityRepository = hospitalBedAvailabilityRepository;
    }

    public List<MedicalSpecializationDTO> findAll() {
        Set<MedicalSpecialization> specializations = hospitalBedAvailabilityRepository.findAll().stream()
                .collect(Collectors.groupingBy(HospitalBedAvailability::getSpecialization))
                .keySet();
        return MedicalSpecializationDtoMapper.convertToDTO(specializations);
    }
}
