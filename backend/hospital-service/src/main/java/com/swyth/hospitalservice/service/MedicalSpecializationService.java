package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.MedicalSpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicalSpecializationService {

    private final MedicalSpecializationRepository medicalSpecializationRepository;

    public MedicalSpecializationService(MedicalSpecializationRepository medicalSpecializationRepository) {
        this.medicalSpecializationRepository = medicalSpecializationRepository;
    }

    public List<MedicalSpecializationDTO> findAll() {
        Set<MedicalSpecialization> specializations = new HashSet<>(medicalSpecializationRepository.findAll());

        // Check if specializations list is empty
        if (specializations.isEmpty()) {
            throw new ResourceNotFoundException("No medical specializations found");
        }

        return MedicalSpecializationDtoMapper.convertToDTO(specializations);
    }

    public MedicalSpecializationDTO findById(Long id) {
        MedicalSpecialization specialization = medicalSpecializationRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Medical Specialization not found for id: " + id));
        return MedicalSpecializationDtoMapper.convertToDTO(specialization);
    }
}
