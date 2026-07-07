package com.example.sapt.services;

import com.example.sapt.dto.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();

    public List<TaskDTO> getTasks(){
        log.info("Getting tasks:");
        return tasks;
    }

    public List<TaskDTO> addTask(TaskDTO taskDTO) {
        TaskDTO newTask = buildTask(taskDTO);
        tasks.add(newTask);
        log.info("Added Task: {} " , newTask);
        return tasks;
    }

    private TaskDTO buildTask(TaskDTO taskDTO) {
        return TaskDTO.builder()
                .id(taskDTO.getId())
                .content(taskDTO.getContent())
                .status(taskDTO.getStatus())
                .dueDate(LocalDateTime.now())
                .build();
    }

    public void update(Long id, TaskDTO taskDTO){
        TaskDTO updatedTask = taskDTO;
        for( var i : tasks){
            if(i.getId().equals(id)){
                i.setContent(taskDTO.getContent());
                i.setStatus(taskDTO.getStatus());
                i.setDueDate(taskDTO.getDueDate());
                updatedTask = i;
                break;
            }
        }
        log.info("Updating Task: {} " , updatedTask);
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
        for( var i : tasks){
            if(Objects.equals(i.getId(), id)){
                return i;
            }
        }
        return null;
    }

    public void deleteTask(Long id){
        for( var i : tasks){
            if(Objects.equals(i.getId(), id)){
                tasks.remove(i);
                break;
            }
        }
    }

    public List<TaskDTO> getStatusTasks(String status){
        List<TaskDTO> filtered = new ArrayList<>();
        for(var i : tasks){
            if(i.getStatus().equalsIgnoreCase(status)){
                filtered.add(i);
            }
        }
        return filtered;
    }

    public List<TaskDTO> updateTaskByObject(String status, String content){
        for(var i : tasks){
            i.setStatus(status);
            i.setContent(content);
        }
        return tasks;
    }
}
