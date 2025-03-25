package com.swyth.emergencyservice.repository;

import com.swyth.emergencyservice.entity.BedReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedReservationRepository extends JpaRepository<BedReservation, Long> {
}
