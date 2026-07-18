package com.example.sapt.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "birth_date")
    LocalDateTime birthDate;

    @Column(name = "is_internal")
    @Convert(converter = NumericBooleanConverter.class) // trebuie sa fac conversie automat, altfel nu vrea :,)
    boolean isInternal;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "created_by", nullable = false)
    String createdBy;

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_updated_by", nullable = false)
    private String lastUpdatedBy;

    @Column(name = "created_by_fullname")
    private String createdByFullname;
}
