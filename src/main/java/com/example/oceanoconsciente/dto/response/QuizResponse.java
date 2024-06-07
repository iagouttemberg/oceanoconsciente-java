package com.example.oceanoconsciente.dto.response;

import lombok.Builder;

@Builder
public record QuizResponse(
        Long id,
        Integer pontuacao,
        UsuarioResponse usuario
) {
}
