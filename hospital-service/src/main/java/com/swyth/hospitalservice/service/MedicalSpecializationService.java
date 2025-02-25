package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.repository.MedicalSpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalSpecializationService {

    private final MedicalSpecializationRepository medicalSpecializationRepository;

    public MedicalSpecializationService(MedicalSpecializationRepository medicalSpecializationRepository) {
        this.medicalSpecializationRepository = medicalSpecializationRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicalSpecializationDTO> findAll() {
        List<MedicalSpecialization> specializations = medicalSpecializationRepository.findAll();
        return MedicalSpecializationDtoMapper.convertToDTO(specializations);
    }
}
