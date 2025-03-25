package com.swyth.emergencyservice.feign;

import com.swyth.emergencyservice.config.FeignConfig;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bed-availability", url = "http://localhost:8081", configuration = FeignConfig.class)
public interface HospitalBedAvailabilityRestClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/bed-availabilities/check", consumes = "application/json")
    Boolean checkHospitalBedAvailability(
            @RequestParam("medicalSpecializationId") Long medicalSpecializationId,
            @RequestParam("hospitalId") Long hospitalId);
}
