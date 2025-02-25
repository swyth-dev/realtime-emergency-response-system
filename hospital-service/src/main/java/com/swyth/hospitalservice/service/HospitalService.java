package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.HospitalDtoMapper;
import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository, MedicalSpecializationService medicalSpecializationService) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<HospitalDTO> findAll() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return HospitalDtoMapper.convertToDTO(hospitals);
    }


}
