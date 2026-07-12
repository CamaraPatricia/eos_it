package com.example.sapt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO (Long userId,
                       @NotBlank String username,
                       LocalDateTime birthDate,
                       boolean isInternal,
                       LocalDateTime creationDate,
                       LocalDateTime lastUpdateDate) {
}
