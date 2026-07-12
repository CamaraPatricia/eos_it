package com.example.sapt.controller;

import com.example.sapt.dto.StatusTypeDTO;
import com.example.sapt.services.StatusTypeService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    public StatusTypeController(StatusTypeService statusTypeService) {
        this.statusTypeService = statusTypeService;
    }

    @GetMapping
    public List<StatusTypeDTO> getAllStatuses() {
        return statusTypeService.getAllStatuses();
    }

    @PostMapping
    public StatusTypeDTO createStatus(@RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.createStatuses(statusTypeDTO);
    }

    @PutMapping("/{id}")
    public StatusTypeDTO updateStatus(@PathVariable String id, @RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.updateStatus(id, statusTypeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable String id) {
        statusTypeService.deleteStatus(id);
    }

    @DeleteMapping
    public void deleteStatusesByName(@RequestParam String statusName) {
        statusTypeService.deleteStatusesByName(statusName);
    }

    @DeleteMapping("/by-created-by")
    public void deleteStatusesByCreatedBy(@RequestParam String createdBy) {
        statusTypeService.deleteStatusesByCreatedBy(createdBy);
    }
}
