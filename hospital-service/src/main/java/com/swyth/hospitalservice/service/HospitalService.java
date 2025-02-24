package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.entity.Hospital;
import com.swyth.hospitalservice.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }


}
