package com.swyth.hospitalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalMedicalSpecializationsKey implements Serializable {

    @Column(name = "hospital_id")
    Long hospitalId;

    @Column(name = "medical_specialization_id")
    Long medicalSpecializationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HospitalMedicalSpecializationsKey that = (HospitalMedicalSpecializationsKey) o;
        return Objects.equals(hospitalId, that.hospitalId) &&
                Objects.equals(medicalSpecializationId, that.medicalSpecializationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hospitalId, medicalSpecializationId);
    }
}
