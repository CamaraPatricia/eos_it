package com.example.sapt.cutomDTO;

import jakarta.validation.constraints.NotBlank;

public record TaskNameUpdateReqDTO(@NotBlank String taskName){
}
