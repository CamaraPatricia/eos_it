package com.example.sapt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusType {
    @Id
    @Column(name = "STATUS_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statusTypeId;

    @Column(name = "STATUS_NAME", nullable = false)
    private String statusName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_date")
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "last_update_date")
    @Builder.Default //UpdateTimestamp
    private LocalDateTime lastUpdatedDate = LocalDateTime.now();
}
