package com.example.sapt.controller;

import com.example.sapt.cutomDTO.TaskAssignationUpdateRequestDTO;
import com.example.sapt.cutomDTO.TaskDueDateUptateReqDTO;
import com.example.sapt.cutomDTO.TaskNameUpdateReqDTO;
import com.example.sapt.dto.CreateTaskRequestDTO;
import com.example.sapt.dto.TaskResponseDTO;
import com.example.sapt.repositories.TaskRepository;
import com.example.sapt.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://localhost:4200",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
public class TaskController {
    private final TaskService taskService;

    //operatii CRUD ---> modificate pe BD
    @GetMapping
    public List<TaskResponseDTO> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable @NotNull Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/by-user")
    public List<TaskResponseDTO> getTasksByUserId(@RequestParam @NotNull Long userId){
        return taskService.getTasksByUserId(userId);
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody CreateTaskRequestDTO taskResponseDTO) {
        return taskService.addTask(taskResponseDTO);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody @Valid CreateTaskRequestDTO req) {
        taskService.update(id, req);
    }

    @PatchMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody TaskAssignationUpdateRequestDTO taskAssign){
        taskService.update(id, taskAssign);
    }

    @PutMapping("/{id}/task-name")
    public TaskResponseDTO updateTaskName(@PathVariable Long id, @RequestBody TaskNameUpdateReqDTO taskName){
        return taskService.updateTaskName(id, taskName);
    }

    @PutMapping("/{id}/due-date")
    public TaskResponseDTO updateDueDate(@PathVariable Long id, @RequestBody TaskDueDateUptateReqDTO dueDateUptate){
        return taskService.updateDueDate(id, dueDateUptate.dueDate());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    // de adaugat doar pentru userul conectat
    @DeleteMapping
    public int deleteTasksByStatus(@RequestParam String statusName){
        return taskService.deleteTasksByStatus(statusName);
    }

    @DeleteMapping("/delete-for-user/{userId}")
    public int deleteTasksForUser(@PathVariable Long userId){
        return taskService.deleteTasksForUser(userId);
    }

    @GetMapping("/by-status")
    public List<TaskResponseDTO> getTasksByStatus(@RequestParam @NotBlank String status) {
        return taskService.getStatusTasks(status);
    }

    @PostMapping("/more")
    public void createTasks(@RequestBody @NotNull List<CreateTaskRequestDTO> taskRequestDTOS) {
        for (CreateTaskRequestDTO taskRequestDTO : taskRequestDTOS) {
            taskService.addTask(taskRequestDTO);
        }
    }

//    alte operatii
//    @PutMapping("/upsert/{id}")
//    public void upsertTask(@PathVariable @NotNull Long id, @RequestBody @Valid TaskDTO taskDTO){
//        taskService.upsert(id, taskDTO);
//    }
//
//    @PutMapping("/update-by-obj")
//    public List<TaskDTO> updateTaskByObject(@RequestBody UpdateRequest updateRequest) {
//        return taskService.updateTaskByObject(updateRequest.status(), updateRequest.content());
//    }
}

