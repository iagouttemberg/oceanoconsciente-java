package com.example.oceanoconsciente.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(
        @Positive
        @NotNull
        Long id
) {
}
