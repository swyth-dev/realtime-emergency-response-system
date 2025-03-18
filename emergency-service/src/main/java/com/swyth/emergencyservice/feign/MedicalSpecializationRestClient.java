package com.swyth.emergencyservice.feign;

import com.swyth.emergencyservice.config.FeignConfig;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "specializations", url = "http://localhost:8081", configuration = FeignConfig.class)
public interface MedicalSpecializationRestClient {
    @RequestMapping(method = RequestMethod.GET, value = "/specializations/{id}", consumes = "application/json")
    MedicalSpecialization getMedicalSpecializationById(@PathVariable("id") Long id);
}
