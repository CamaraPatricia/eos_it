package com.example.sapt.services;

import com.example.sapt.cutomDTO.TaskAssignUpdate;
import com.example.sapt.cutomDTO.TaskNameUpdate;
import com.example.sapt.dto.TaskDTO;
import com.example.sapt.entities.Task;
import com.example.sapt.mapper.TaskMapper;
import com.example.sapt.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service pentru task-uri
 *
 * Operatii:
 *   - getTasks: returneaza toate task-urile
 *   - create: creationDate si created_by sunt default
 *   - update --> trebuie sa modifice last_update_date ( by ramane default)
 *      * statusTypeId pentru userId (pentru alterari viitoare)
 *      * dueDate
 *      * taskName
 *
 *   - delete
 *      * pentru un utilizator
 *      * care sunt un anumit status
 *      * unul dupa id
 */

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> getTasks(){
        log.info("Getting tasks:");
        tasks = taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
        return tasks;
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        taskRepository.save(taskMapper.toEntity(taskDTO));
        log.info("Added Task: {} " , taskDTO);
        return taskDTO;
    }

    //update statusId sau userId pentru taskId
    @Transactional
    public void update(Long id, TaskAssignUpdate taskAssign){
        Optional<Task> taskEntity = taskRepository.findById(id); //am cautat dupa id doar ca sa vedem daca exista

        if(taskEntity.isPresent()){
            Task task = taskEntity.get();
            boolean modified = false;

            if(taskAssign.statusId() != null) {
                task.setStatusTypeId(taskAssign.statusId());
                modified = true;
            }
            if(taskAssign.userId() != null) {
                task.setUserId(taskAssign.userId());
                modified = true;
            }

            if(modified) {
                task.setLastUpdateDate(LocalDateTime.now());
                log.info("Task with id found. Updated task: {}", id);
            }
        } else {
            log.info("Task with id {} not found", id);
        }
    }

    @Transactional
    public TaskDTO updateTaskName(Long id, TaskNameUpdate taskName){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setName(taskName.taskName());
        task.setLastUpdateDate(LocalDateTime.now());
        log.info("Task with id {} updated with new name: {}", id, taskName);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO updateDueDate(Long id, LocalDateTime dueDate){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDueDate(dueDate);
        task.setLastUpdateDate(LocalDateTime.now());
        log.info("Task with id {} updated with new due date: {}", id, dueDate);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public int deleteTasksByStatus(String statusName) {
        return taskRepository.deleteAllByStatusName(statusName);
    }

    @Transactional
    public int deleteTasksForUser(Long userId){
        return taskRepository.deleteAllByUserId(userId);
    }

    public void upsert(Long id, TaskDTO taskDTO){
        boolean exists = false;
        for(var i : tasks){
            if(i.getId().equals(id)){
                i.setContent(taskDTO.getContent());
                i.setStatus(taskDTO.getStatus());
                i.setDueDate(taskDTO.getDueDate());
                exists = true;
                break;
            }
        }
        if(!exists){
            tasks.add(taskDTO);
        }
    }

    public TaskDTO getTaskById(Long id){
        return taskMapper.toDTO(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id)));
    }



    public List<TaskDTO> getStatusTasks(String status){
        return taskRepository.findByStatusName(status).stream().map(taskMapper::toDTO).toList();
    }

    public List<TaskDTO> updateTaskByObject(String status, String content){
        for(var i : tasks){
            i.setStatus(status);
            i.setContent(content);
        }
        return tasks;
    }
}
