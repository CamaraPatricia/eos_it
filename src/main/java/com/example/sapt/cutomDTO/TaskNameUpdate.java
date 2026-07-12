package com.example.sapt.cutomDTO;

import jakarta.validation.constraints.NotBlank;

public record TaskNameUpdate (@NotBlank String taskName){
}
