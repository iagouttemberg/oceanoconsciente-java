package com.example.oceanoconsciente.dto.request;

import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(
        @NotNull
        String cep,
        @NotNull
        String cidade,
        @NotNull
        String rua,
        @NotNull
        String numero,
        String complemento
) {
}
