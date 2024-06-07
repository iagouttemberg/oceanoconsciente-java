package com.example.oceanoconsciente.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record QuizRequest(
        Integer pontuacao,
        @Valid
        @NotNull
        AbstractRequest usuario
) {
}
