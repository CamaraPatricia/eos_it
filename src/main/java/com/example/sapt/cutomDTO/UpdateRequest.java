package com.example.sapt.cutomDTO;

import jakarta.validation.constraints.NotBlank;

public record UpdateRequest (@NotBlank  String content, @NotBlank String status) {
}
