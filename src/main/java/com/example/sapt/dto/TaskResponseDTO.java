package com.example.sapt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO pentru raspunsul task-urilor
 *
 * Contine toate atributele din entitate
 * pentru afisarea componentelor de tip Task in interfata
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TaskResponseDTO {
    private Long id;
    @NotBlank private String taskName;
    @NotBlank private String statusTypeId;
    private String statusType;
    private Long userId;
    @NotNull private LocalDateTime dueDate;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullname;
}
