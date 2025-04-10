package com.swyth.hospitalservice.service;

import com.swyth.hospitalservice.dto.MedicalSpecializationDTO;
import com.swyth.hospitalservice.dto.MedicalSpecializationDtoMapper;
import com.swyth.hospitalservice.entity.MedicalSpecialization;
import com.swyth.hospitalservice.exception.ResourceNotFoundException;
import com.swyth.hospitalservice.repository.MedicalSpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class responsible for managing medical specializations.
 *
 * This service interacts with the {@code MedicalSpecializationRepository} to perform
 * operations related to the retrieval and management of medical specializations in the system.
 * It serves as a business logic layer between the persistence layer and the higher-level
 * application layers, such as controllers.
 *
 * Responsibilities:
 * - Fetching all available medical specializations from the repository.
 * - Fetching specific medical specializations based on their unique IDs.
 * - Throwing appropriate exceptions, such as {@code ResourceNotFoundException}, when required
 *   resources are not found in the repository.
 * - Converting entities to DTOs for external use through the {@code MedicalSpecializationDtoMapper}.
 *
 * Behavior:
 * - The {@code findAll} method retrieves all available medical specializations. If no
 *   specializations are found, it throws a {@code ResourceNotFoundException}.
 * - The {@code findById} method retrieves a specific medical specialization using its ID.
 *   If the ID does not correspond to any specialization, it throws a {@code ResourceNotFoundException}.
 *
 * Validators and Exceptions:
 * - Throws {@code ResourceNotFoundException} when no data is found for requested IDs
 *   or when there are no medical specializations available.
 *
 * Dependencies:
 * - {@code MedicalSpecializationRepository}: Used to perform data access operations.
 * - {@code MedicalSpecializationDtoMapper}: Used to map the entity to its corresponding DTO.
 *
 * Typical Use Cases:
 * - Retrieving a list of all medical specializations along with associated details.
 * - Fetching detailed information for a specific medical specialization.
 */
@Service
public class MedicalSpecializationService {

    private final MedicalSpecializationRepository medicalSpecializationRepository;

    public MedicalSpecializationService(MedicalSpecializationRepository medicalSpecializationRepository) {
        this.medicalSpecializationRepository = medicalSpecializationRepository;
    }

    public List<MedicalSpecializationDTO> findAll() {
        Set<MedicalSpecialization> specializations = new HashSet<>(medicalSpecializationRepository.findAll());

        // Check if specializations list is empty
        if (specializations.isEmpty()) {
            throw new ResourceNotFoundException("No medical specializations found");
        }

        return MedicalSpecializationDtoMapper.convertToDTO(specializations);
    }

    public MedicalSpecializationDTO findById(Long id) {
        MedicalSpecialization specialization = medicalSpecializationRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Medical Specialization not found for id: " + id));
        return MedicalSpecializationDtoMapper.convertToDTO(specialization);
    }
}
