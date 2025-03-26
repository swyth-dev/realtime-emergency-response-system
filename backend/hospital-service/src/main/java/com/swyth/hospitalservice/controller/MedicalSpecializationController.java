package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.service.MedicalSpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/v1/specializations")
@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO: don't let it like that in Production env
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
    @GetMapping("")
    public ResponseEntity<List<MedicalSpecializationDTO>> getMedicalSpecializations() {
        try {
            List<MedicalSpecializationDTO> medicalSpecializations = medicalSpecializationService.findAll();
            return ResponseEntity.ok(medicalSpecializations);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicalSpecializationDTO> getMedicalSpecializationById(@PathVariable Long id) {
        try {
            MedicalSpecializationDTO medicalSpecialization = medicalSpecializationService.findById(id);
            return ResponseEntity.ok(medicalSpecialization);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
