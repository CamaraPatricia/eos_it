package com.example.sapt.mapper;

import com.example.sapt.dto.CreateTaskRequestDTO;
import com.example.sapt.dto.TaskResponseDTO;
import com.example.sapt.entities.Task;
import com.example.sapt.repositories.StatusTypeRepository;
import com.example.sapt.repositories.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Mapper pentru task-uri
 *
 * Converteste entitatea Task in TaskResponseDTO si invers
 */

@Component
public class TaskMapper {
    private final UserRepository userRepository;
    private final StatusTypeRepository statusTypeRepository;

    public TaskMapper(UserRepository userRepository, StatusTypeRepository statusTypeRepository) {
        this.userRepository = userRepository;
        this.statusTypeRepository = statusTypeRepository;
    }

    public TaskResponseDTO toDTO(Task task){
        return TaskResponseDTO.builder()
                .userId(task.getUser().getUserId())
                .id(task.getTaskId())
                .taskName(task.getName())
                .dueDate(task.getDueDate())
                .statusTypeId(task.getStatusType().getStatusTypeId())
                .statusType(task.getStatusType().getStatusName())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .lastUpdateDate(task.getLastUpdateDate())
                .createdBy(task.getCreatedBy())
                .creationDate(task.getCreationDate())
                .createdByFullname(task.getCreatedByFullname())
                .build();
    }

    public Task toEntity(TaskResponseDTO taskResponseDTO){
        return Task.builder()
                .user((userRepository.findById(taskResponseDTO.getUserId())).orElseThrow(() -> new RuntimeException("User not found with id: " + taskResponseDTO.getUserId())))
                .name(taskResponseDTO.getTaskName())
                .dueDate(taskResponseDTO.getDueDate())
                .statusType((statusTypeRepository.findById(taskResponseDTO.getStatusTypeId())).
                        orElseThrow(() -> new RuntimeException("StatusType not found with id: " + taskResponseDTO.getStatusTypeId())))
                .lastUpdatedBy(taskResponseDTO.getLastUpdatedBy())
                .lastUpdateDate(taskResponseDTO.getLastUpdateDate())
                .createdBy(taskResponseDTO.getCreatedBy())
                .createdByFullname(taskResponseDTO.getCreatedByFullname())
                .creationDate(taskResponseDTO.getCreationDate())
                .build();
    }

    public Task toEntity(CreateTaskRequestDTO taskResponseDTO){
        return Task.builder()
                .user((userRepository.findById(taskResponseDTO.userId())).orElseThrow(() -> new RuntimeException("User not found with id: " + taskResponseDTO.userId())))
                .name(taskResponseDTO.taskName())
                .dueDate(taskResponseDTO.dueDate())
                .statusType(statusTypeRepository.findByStatusName("Not Started"))
                .build();
    }
}
