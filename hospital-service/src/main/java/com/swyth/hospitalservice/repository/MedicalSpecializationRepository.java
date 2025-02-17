package com.swyth.hospitalservice.repository;

import com.swyth.hospitalservice.entity.MedicalSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicalSpecializationRepository extends JpaRepository<MedicalSpecialization,Long> {
}
