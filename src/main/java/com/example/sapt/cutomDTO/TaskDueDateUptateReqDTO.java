package com.example.sapt.cutomDTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskDueDateUptateReqDTO (@NotNull LocalDateTime dueDate) {
}
