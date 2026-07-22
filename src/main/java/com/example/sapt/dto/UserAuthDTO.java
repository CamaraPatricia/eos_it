package com.example.sapt.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Builder
public record UserAuthDTO  (
        String email,
        String password
){
}
