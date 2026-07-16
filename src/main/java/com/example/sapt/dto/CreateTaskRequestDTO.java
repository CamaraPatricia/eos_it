package com.example.sapt.dto;

import java.time.LocalDateTime;

/**
 * DTO pentru cererea de crearea unui task
 */
public record CreateTaskRequestDTO(String taskName,
                                   Long userId,
                                   LocalDateTime dueDate) {
}
