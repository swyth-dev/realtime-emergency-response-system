package com.swyth.emergencyservice.service;

import com.swyth.emergencyservice.entity.BedReservation;
import com.swyth.emergencyservice.feign.MedicalSpecializationRestClient;
import com.swyth.emergencyservice.model.MedicalSpecialization;
import com.swyth.emergencyservice.repository.BedReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class BedReservationService {
    private final BedReservationRepository bedReservationRepository;
    private final MedicalSpecializationRestClient medicalSpecializationRestClient;

    public BedReservationService(BedReservationRepository bedReservationRepository, MedicalSpecializationRestClient medicalSpecializationRestClient) {
        this.bedReservationRepository = bedReservationRepository;
        this.medicalSpecializationRestClient = medicalSpecializationRestClient;
    }

    public String createBedReservation(Long hospitalId, Long medicalSpecializationId) {

        // Step 1: Check if Medical Specialization exists
        MedicalSpecialization specialization = medicalSpecializationRestClient.getMedicalSpecializationById(medicalSpecializationId);
        if (specialization == null) {
            return "Specialization not found";
        }

        // Step 2: Check if the Hospital ID is linked to the Specialization
        boolean isHospitalLinked = specialization.getHospitals().stream()
                .anyMatch(hospitalAvailability -> hospitalAvailability.getId().equals(hospitalId));
        if (!isHospitalLinked) {
            return "Hospital ID is not linked to the specialization";
        }

        // TODO: Check if Bed is available for this hospital and specialization
        // TODO : Migrer cette logique métier: c le serruce hospital qui est responsbale de cette logique.


        // If all checks pass, proceed to create a bed reservation (logic to be added)
        // Example placeholder for bed reservation logic
        // Proceed to save the reservation
        BedReservation reservation = new BedReservation(hospitalId, medicalSpecializationId);

        bedReservationRepository.save(reservation);

        return reservation.toString();
    }
}
