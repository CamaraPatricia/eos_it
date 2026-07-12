package com.example.sapt.cutomDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskAssignUpdate (String statusId, Long userId) {
}
