package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.service.MedicalSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class MedicalSpecializationController {
    @Autowired
    private MedicalSpecializationService medicalSpecializationService;

    @GetMapping("/specializations")
    public ResponseEntity<List<MedicalSpecialization>> getMedicalSpecializations() {
        List<MedicalSpecialization> medicalSpecializationsReponse = medicalSpecializationService.findAll();
        System.out.println("Fetched Data: " + medicalSpecializationsReponse);
        return ResponseEntity.ok(medicalSpecializationsReponse);
    }
}
