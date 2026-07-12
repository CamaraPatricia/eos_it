package com.example.sapt.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long taskId;

    @Column(name = "task_name", nullable = false)
    String name;

    @Column(name = "status_type_id")
    String statusTypeId;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "due_date")
    LocalDateTime dueDate;

    @Column(name = "created_by", nullable = false
    , insertable = false, updatable = false)
    String createdBy;

    @Column(name = "creation_date", nullable = false)
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "last_update_date", nullable = false, insertable = false, updatable = false)
    @Builder.Default
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @Column(name = "last_updated_by", nullable = false, insertable = false, updatable = false)
    private String lastUpdatedBy;

    @Column(name = "created_by_fullname")
    private String createdByFullname;
}
