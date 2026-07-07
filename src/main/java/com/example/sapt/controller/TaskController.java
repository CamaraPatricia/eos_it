package com.example.sapt.controller;

import com.example.sapt.dto.TaskDTO;
import com.example.sapt.dto.UpdateRequest;
import com.example.sapt.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //operatii CRUD
    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping
    public List<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO){
        taskService.update(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    //alte operatii
    @PutMapping("/upsert/{id}")
    public void upsertTask(@PathVariable @NotNull Long id, @RequestBody @Valid TaskDTO taskDTO){
        taskService.upsert(id, taskDTO);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable @NotNull Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/statuses")
    public List<TaskDTO> getTasksByStatus(@RequestParam @NotBlank String status) {
        return taskService.getStatusTasks(status);
    }

    @PutMapping("/update-by-obj")
    public List<TaskDTO> updateTaskByObject(@RequestBody UpdateRequest updateRequest) {
        return taskService.updateTaskByObject(updateRequest.status(), updateRequest.content());
    }

    @PostMapping("/more")
    public void createTasks(@RequestBody @NotNull List<TaskDTO> taskDTOs) {
        for (TaskDTO taskDTO : taskDTOs) {
            taskService.addTask(taskDTO);
        }
    }
}

