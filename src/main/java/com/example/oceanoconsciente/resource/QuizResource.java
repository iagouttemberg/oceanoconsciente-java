package com.example.oceanoconsciente.resource;

import com.example.oceanoconsciente.dto.request.QuizRequest;
import com.example.oceanoconsciente.dto.response.QuizResponse;
import com.example.oceanoconsciente.entity.Quiz;
import com.example.oceanoconsciente.entity.Usuario;
import com.example.oceanoconsciente.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/quizzes")
public class QuizResource implements ResourceDTO<QuizRequest, QuizResponse> {
    @Autowired
    private QuizService quizService;

    @Operation(summary = "Get all quizzes")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<QuizResponse>>> findAll(

            @RequestParam(name = "usuario.nome", required = false) String nomeUsuario,
            @RequestParam(name = "usuario.cpf", required = false) String cpfUsuario,
            @RequestParam(name = "usuario.email", required = false) String emailUsuario,
            @RequestParam(name = "usuario.senha", required = false) String senhaUsuario,
            @RequestParam(name = "pontuacao", required = false) Integer pontuacao

    ) {

        var usuario = Usuario.builder()
                .nome(nomeUsuario)
                .cpf(cpfUsuario)
                .email(emailUsuario)
                .senha(senhaUsuario)
                .build();

        var exampleQuiz = Quiz.builder()
                .usuario(usuario)
                .pontuacao(pontuacao)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Quiz> example = Example.of(exampleQuiz, matcher);
        Collection<Quiz> quizzes = quizService.findAll(example);

        var response = quizzes.stream()
                .map(q -> EntityModel.of(quizService.toResponse(q),
                        Link.of(ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{id}")
                                .buildAndExpand(q.getId())
                                .toUriString())
                                .withSelfRel()))
                .toList();

        var selfLink = Link.of(ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUriString())
                .withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(response, selfLink));
    }

    @Operation(summary = "Save a new quiz")
    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<EntityModel<QuizResponse>> save(@RequestBody @Valid QuizRequest r) {
        var entity = quizService.toEntity(r);
        var saved = quizService.save(entity);
        var response = quizService.toResponse(saved);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(saved.getId())
                        .toUriString())
                .withSelfRel();

        var model = EntityModel.of(response, selfLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @Operation(summary = "Find a quiz by ID")
    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<EntityModel<QuizResponse>> findById(@PathVariable Long id) {
        var entity = quizService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = quizService.toResponse(entity);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .build()
                        .toUriString())
                .withSelfRel();

        return ResponseEntity.ok(EntityModel.of(response, selfLink));
    }
}
