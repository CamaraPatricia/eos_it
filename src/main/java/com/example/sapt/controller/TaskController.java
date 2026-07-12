package com.example.sapt.controller;

import com.example.sapt.cutomDTO.TaskAssignUpdate;
import com.example.sapt.cutomDTO.TaskNameUpdate;
import com.example.sapt.dto.TaskDTO;
import com.example.sapt.cutomDTO.UpdateRequest;
import com.example.sapt.entities.Task;
import com.example.sapt.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //operatii CRUD ---> modificate pe BD
    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @PatchMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody TaskAssignUpdate taskAssign){
        taskService.update(id, taskAssign);
    }

    @PutMapping("/{id}/task-name")
    public TaskDTO updateTaskName(@PathVariable Long id, @RequestBody TaskNameUpdate taskName){
        return taskService.updateTaskName(id, taskName);
    }

    @PutMapping("/{id}/due-date")
    public TaskDTO updateDueDate(@PathVariable Long id, @RequestParam LocalDateTime dueDate){
        return taskService.updateDueDate(id, dueDate);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @DeleteMapping
    public int deleteTasksByStatus(@RequestParam String statusName){
        return taskService.deleteTasksByStatus(statusName);
    }

    @DeleteMapping("/delete-for-user/{userId}")
    public int deleteTasksForUser(@PathVariable Long userId){
        return taskService.deleteTasksForUser(userId);
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

