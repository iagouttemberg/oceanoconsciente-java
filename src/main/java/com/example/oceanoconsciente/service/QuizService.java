package com.example.oceanoconsciente.service;

import com.example.oceanoconsciente.dto.request.QuizRequest;
import com.example.oceanoconsciente.dto.response.QuizResponse;
import com.example.oceanoconsciente.entity.Quiz;
import com.example.oceanoconsciente.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QuizService implements ServiceDTO<Quiz, QuizRequest, QuizResponse> {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Override
    public Collection<Quiz> findAll(Example<Quiz> example) {
        return quizRepository.findAll(example);
    }

    @Override
    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Override
    public Quiz save(Quiz e) {
        return quizRepository.save(e);
    }

    @Override
    public Quiz toEntity(QuizRequest dto) {

        var usuario = usuarioService.findById(dto.usuario().id());

        return Quiz.builder()
                .pontuacao(dto.pontuacao())
                .usuario(usuario)
                .build();

    }

    @Override
    public QuizResponse toResponse(Quiz e) {

        var usuario = usuarioService.toResponse(e.getUsuario());

        return QuizResponse.builder()
                .id(e.getId())
                .pontuacao(e.getPontuacao())
                .usuario(usuario)
                .build();
    }

    public Integer sumPointsByUsuarioId(Long ID_USUARIO) {
        return quizRepository.sumPointsByUsuarioId(ID_USUARIO);
    }
}
