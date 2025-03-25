package com.swyth.hospitalservice.dto;

import com.swyth.hospitalservice.entity.Hospital;

public class NearestHospitalDtoMapper {
    private NearestHospitalDtoMapper() {
    }

    public static NearestHospitalDTO convertToDTO(Hospital nearestHospital) {
        // Convert the nearest hospital to the DTO
        return new NearestHospitalDTO(
                nearestHospital.getId(),
                nearestHospital.getName(),
                nearestHospital.getAddress(),
                nearestHospital.getPostCode(),
                nearestHospital.getCity(),
                nearestHospital.getLatitude(),
                nearestHospital.getLongitude()
        );
    }
}
