package com.example.oceanoconsciente.dto.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String senha,
        EnderecoResponse endereco
) {
}
