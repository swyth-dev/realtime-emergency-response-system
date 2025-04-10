package com.swyth.emergencyservice.feign;

import com.swyth.emergencyservice.config.FeignConfig;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;

/**
 * Feign client interface for interacting with the hospital-service API to check bed availability.
 *
 * This interface defines a method to communicate with the hospital-service endpoint
 * responsible for verifying availability of hospital beds based on the provided hospital ID
 * and medical specialization ID. The underlying HTTP call is made using Feign, and
 * request/response handling is configured with the FeignConfig class.
 *
 * The method in this interface sends an HTTP POST request to the /v1/bed-availabilities/check endpoint
 * and expects a response indicating whether a bed is available or not.
 *
 * The Feign client is annotated with @FeignClient to mark it as a declarative REST client, and
 * utilizes configurations from the FeignConfig class for decoding, encoding, and error handling.
 */
@FeignClient(name = "hospital-service", configuration = FeignConfig.class)
public interface HospitalBedAvailabilityRestClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/bed-availabilities/check", consumes = "application/json")
    Boolean checkHospitalBedAvailability(
            @RequestParam("medicalSpecializationId") Long medicalSpecializationId,
            @RequestParam("hospitalId") Long hospitalId);
}
