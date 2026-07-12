package com.example.sapt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TaskDTO {
    private Long id;
    @NotBlank private String content;
    @NotNull private LocalDateTime dueDate;
    @NotBlank private String status;
    private Long userId;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
}
