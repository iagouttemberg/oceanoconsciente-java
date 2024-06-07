package com.example.oceanoconsciente.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotNull
        String nome,
        @NotNull
        String cpf,
        @NotNull
        String email,
        @NotNull
        String senha,
        @Valid
        @NotNull
        EnderecoRequest endereco
) {
}
