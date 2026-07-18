package com.example.sapt.dto;

import java.time.LocalDateTime;

public record UserReqDTO(
        LocalDateTime birthDate,
        String email,
        String username,
        String password
) {}
