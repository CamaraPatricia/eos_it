package com.example.sapt.services;

import com.example.sapt.cutomDTO.TaskAssignationUpdateRequestDTO;
import com.example.sapt.cutomDTO.TaskNameUpdateReqDTO;
import com.example.sapt.dto.CreateTaskRequestDTO;
import com.example.sapt.dto.TaskResponseDTO;
import com.example.sapt.entities.Task;
import com.example.sapt.mapper.TaskMapper;
import com.example.sapt.repositories.StatusTypeRepository;
import com.example.sapt.repositories.TaskRepository;
import com.example.sapt.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private List<TaskResponseDTO> tasks = new ArrayList<>();
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final StatusTypeRepository statusTypeRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, StatusTypeRepository statusTypeRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.statusTypeRepository = statusTypeRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getTasks(){
        log.info("Getting tasks:");
        tasks = taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
        return tasks;
    }

    @Transactional
    public TaskResponseDTO addTask(CreateTaskRequestDTO taskRequestDTO) {
        taskRepository.save(taskMapper.toEntity(taskRequestDTO));
        log.info("Added Task: {} " , taskRequestDTO);
        return taskMapper.toDTO(taskRepository.findByName(taskRequestDTO.taskName()));
    }

    public List<TaskResponseDTO> getTasksByUserId(Long userId){
        return taskRepository.findByUser_UserId(userId).stream().map(taskMapper::toDTO).toList();
    }

    //update statusId sau userId pentru taskId
    @Transactional
    public void update(Long id, TaskAssignationUpdateRequestDTO taskAssign){
        Optional<Task> taskEntity = taskRepository.findById(id); //am cautat dupa id doar ca sa vedem daca exista

        if(taskEntity.isPresent()){
            Task task = taskEntity.get();
            boolean modified = false;

            if(taskAssign.statusId() != null) {
                task.setStatusType((statusTypeRepository.findById(taskAssign.statusId()))
                        .orElseThrow(() -> new RuntimeException("StatusType not found with id: " + taskAssign.statusId())));
                modified = true;
            }
            if(taskAssign.userId() != null) {
                task.setUser((userRepository.findById(taskAssign.userId()))
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + taskAssign.userId())));
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
    public void update(Long id,CreateTaskRequestDTO req){
        Optional<Task> optTask = taskRepository.findById(id); //am cautat dupa id doar ca sa vedem daca exista

        if(optTask.isEmpty()){
            log.info("Task with id {} not found", id);
            throw new RuntimeException("Task not found with id: " + id);
        }
        Task task = optTask.get();
            boolean modified = false;

            if(req.taskName() != null) {
                task.setName(req.taskName());
                modified = true;
            }
            if(req.userId() != null) {
                task.setUser((userRepository.findById(req.userId()))
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + req.userId())));
                modified = true;
            }
            if(req.dueDate() != null) {
                task.setDueDate(req.dueDate());
                modified = true;
            }

            if(modified) {
                task.setLastUpdateDate(LocalDateTime.now());
                log.info("Task with id found. Updated task: {}", task.getTaskId());
            }
    }


    @Transactional
    public TaskResponseDTO updateTaskName(Long id, TaskNameUpdateReqDTO taskName){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setName(taskName.taskName());
        task.setLastUpdateDate(LocalDateTime.now());
        log.info("Task with id {} updated with new name: {}", id, taskName);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskResponseDTO updateDueDate(Long id, LocalDateTime dueDate){
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
        return taskRepository.deleteAllByUser_UserId(userId);
    }

//    public void upsert(Long id, TaskDTO taskDTO){
//        boolean exists = false;
//        for(var i : tasks){
//            if(i.getId().equals(id)){
//                i.setContent(taskDTO.getContent());
//                i.setStatus(taskDTO.getStatus());
//                i.setDueDate(taskDTO.getDueDate());
//                exists = true;
//                break;
//            }
//        }
//        if(!exists){
//            tasks.add(taskDTO);
//        }
//    }

    public TaskResponseDTO getTaskById(Long id){
        return taskMapper.toDTO(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id)));
    }



    public List<TaskResponseDTO> getStatusTasks(String status){
        return taskRepository.findByStatusName(status).stream().map(taskMapper::toDTO).toList();
    }

//    public List<TaskDTO> updateTaskByObject(String status, String content){
//        for(var i : tasks){
//            i.setStatus(status);
//            i.setContent(content);
//        }
//        return tasks;
//    }
}
