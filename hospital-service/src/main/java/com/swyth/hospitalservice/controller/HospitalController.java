package com.swyth.hospitalservice.controller;

import com.swyth.hospitalservice.dto.EmergencyLocationDTO;
import com.swyth.hospitalservice.dto.HospitalDTO;
import com.swyth.hospitalservice.dto.NearestHospitalDTO;
import com.swyth.hospitalservice.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/v1")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hospitals")
    public ResponseEntity<List<HospitalDTO>> getHospitals() {
        List<HospitalDTO> hospitals = hospitalService.findAll();
        return ResponseEntity.ok(hospitals);
    }

    /**
     * POST endpoint to retrieve the nearest hospital based on latitude and longitude.
     * Rationale for POST:
     * - Sensitive data (latitude, longitude) is sent in the request body for better security.
     * - Avoids exposing data in query parameters, which can be logged or cached.
     * - Suitable for operations involving calculations, such as identifying the nearest hospital.
*    * @param locationDTO contains latitude, longitude, and specialization ID.
*    * @return nearest hospital details.
     */
    @PostMapping("/hospitals/nearest")
    public ResponseEntity<NearestHospitalDTO> getNearestHospitals(@Valid @RequestBody EmergencyLocationDTO emergencyLocationDTO) {
        NearestHospitalDTO nearestHospital = hospitalService.findNearestHospital(
                emergencyLocationDTO.getMedicalSpecializationId(),
                emergencyLocationDTO.getLatitude(),
                emergencyLocationDTO.getLongitude()
        );
        return ResponseEntity.ok(nearestHospital);
    }

}
// TODO : Virer tout ce qui ne sert à rien en endpoint