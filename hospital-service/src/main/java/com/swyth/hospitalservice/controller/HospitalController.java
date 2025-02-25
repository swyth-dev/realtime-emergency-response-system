package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    @Transactional(readOnly = true)  // Make sure the database interaction is consistent
    public ResponseEntity<List<HospitalDTO>> getHospitals() {
        List<HospitalDTO> hospitals = hospitalService.findAll();
        return ResponseEntity.ok(hospitals);
    }
}
