package com.example.sapt.dto;

import lombok.Builder;

@Builder
public record AuthResponse (
        UserDTO user,
        String message){
}
