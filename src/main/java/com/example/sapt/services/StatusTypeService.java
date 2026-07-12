package com.example.sapt.services;

import com.example.sapt.dto.StatusTypeDTO;
import com.example.sapt.entities.StatusType;
import com.example.sapt.mapper.StatusTypeMapper;
import com.example.sapt.repositories.StatusTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service pentru gestionarea de statusuri.
 *
 * Pot fi interogate toate
 * creat --> se adauga creation_date sysdate by default (created_by ramane default momentan)
 * sters --> unul singur
 *       --> toate dupa cu un anumit nume
 *       --> toate de la o persoana cu Query custom
 * update --> se poate schimba doar numele statusului
 *        --> se modifica last_updated_date (last_updated_by ramane default momentan)
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeService {
    private final StatusTypeRepository repository;
    private final StatusTypeMapper mapper;

    public List<StatusTypeDTO> getAllStatuses() {
        log.info("Statuses retrieved from database!");
        return  repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public StatusTypeDTO createStatuses(StatusTypeDTO statusTypeDTO) {
        log.info("Creating status type {}", statusTypeDTO);
        StatusType status = mapper.toEntity(statusTypeDTO);
        StatusType savedStatus = repository.save(status);

        return mapper.toDTO(savedStatus);
    }

    @Transactional
    public StatusTypeDTO updateStatus(String id, StatusTypeDTO statusTypeDTO) {
        log.info("Updating status type with id {}", id);

        StatusType status = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        status.setStatusName(statusTypeDTO.getStatusName());
        status.setLastUpdatedDate(LocalDateTime.now());

        return mapper.toDTO(status);
    }

    @Transactional
    public void deleteStatus(String id) {
        log.info("Deleting status type with id {}", id);
        StatusType status = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Status type not found with id: " + id
                        )
                );

        repository.delete(status);
    }

    @Transactional
    public void deleteStatusesByName(String statusName) {
        log.info("Deleting status types with name {}", statusName);
        repository.deleteByStatusName(statusName);
    }

    @Transactional
    public void deleteStatusesByCreatedBy(String createdBy) {
        log.info("Deleting status types created by {}", createdBy);
        int deletedCount = repository.deleteByCreatedBy(createdBy);

        log.info("Deleted {} status types created by {}", deletedCount, createdBy);
    }
}