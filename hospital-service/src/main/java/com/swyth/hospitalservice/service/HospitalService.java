package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.HospitalDtoMapper;
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


}
