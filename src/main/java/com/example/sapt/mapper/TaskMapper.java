package com.example.sapt.mapper;

import com.example.sapt.dto.TaskDTO;
import com.example.sapt.entities.Task;
import com.example.sapt.repositories.TaskRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final TaskRepository taskRepository;

    public TaskMapper(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO toDTO(Task task){
        return TaskDTO.builder()
                .userId(task.getUserId())
                .id(task.getTaskId())
                .content(task.getName())
                .dueDate(task.getDueDate())
                .status(task.getStatusTypeId())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .lastUpdateDate(task.getLastUpdateDate())
                .build();
    }

    public Task toEntity(TaskDTO taskDTO){
        return Task.builder()
                .userId(taskDTO.getUserId())
                .name(taskDTO.getContent())
                .dueDate(taskDTO.getDueDate())
                .statusTypeId(taskDTO.getStatus())
                .lastUpdatedBy(taskDTO.getLastUpdatedBy())
                .lastUpdateDate(taskDTO.getLastUpdateDate())
                .build();
    }
}
