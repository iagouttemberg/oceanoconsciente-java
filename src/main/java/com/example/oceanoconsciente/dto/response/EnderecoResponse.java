package com.example.oceanoconsciente.dto.response;

import lombok.Builder;

@Builder
public record EnderecoResponse(
        Long id,
        String cep,
        String cidade,
        String rua,
        String numero,
        String complemento
) {
}
