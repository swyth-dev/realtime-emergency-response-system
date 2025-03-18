package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.service.MedicalSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class MedicalSpecializationController {
    private final MedicalSpecializationService medicalSpecializationService;

    public MedicalSpecializationController(MedicalSpecializationService medicalSpecializationService) {
        this.medicalSpecializationService = medicalSpecializationService;
    }

    /*
    - Get all specializations
    - Get nearest hospital (param : spec id & son adresse) -> Hopital le plus proche
    - Check if bed availabe for specialization and hospital id
    - Reserver le lit (param : spec id & hospital id) -> Reservation (id reservation, info hopital, info specialization)

    Endpoints en plus :

     */
    @GetMapping("/specializations")
    public ResponseEntity<List<MedicalSpecializationDTO>> getMedicalSpecializations() {
        List<MedicalSpecializationDTO> medicalSpecializations = medicalSpecializationService.findAll();
        return ResponseEntity.ok(medicalSpecializations);
    }
    
    @GetMapping("/specializations/{id}")
    public ResponseEntity<MedicalSpecializationDTO> getMedicalSpecializationById(@PathVariable Long id) {
        MedicalSpecializationDTO medicalSpecialization = medicalSpecializationService.findById(id);
        return ResponseEntity.ok(medicalSpecialization);
    }
}
