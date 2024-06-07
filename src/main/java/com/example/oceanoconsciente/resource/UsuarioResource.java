package com.example.oceanoconsciente.resource;

import com.example.oceanoconsciente.dto.request.UsuarioRequest;
import com.example.oceanoconsciente.dto.response.UsuarioResponse;
import com.example.oceanoconsciente.entity.Endereco;
import com.example.oceanoconsciente.entity.Usuario;
import com.example.oceanoconsciente.service.QuizService;
import com.example.oceanoconsciente.service.UsuarioService;
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
@RequestMapping(value = "/usuarios")
public class UsuarioResource implements ResourceDTO<UsuarioRequest, UsuarioResponse> {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private QuizService quizService;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponse>>> findAll(

            @RequestParam(name = "endereco.cep", required = false) String cepEndereco,
            @RequestParam(name = "endereco.cidade", required = false) String cidadeEndereco,
            @RequestParam(name = "endereco.rua", required = false) String ruaEndereco,
            @RequestParam(name = "endereco.numero", required = false) String numeroEndereco,
            @RequestParam(name = "endereco.complemento", required = false) String complementoEndereco,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "senha", required = false) String senha

    ) {

        var endereco = Endereco.builder()
                .cep(cepEndereco)
                .cidade(cidadeEndereco)
                .rua(ruaEndereco)
                .numero(numeroEndereco)
                .complemento(complementoEndereco)
                .build();

        var usuario = Usuario.builder()
                .endereco(endereco)
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .senha(senha)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Usuario> example = Example.of(usuario, matcher);
        Collection<Usuario> usuarios = usuarioService.findAll(example);

        var response = usuarios.stream()
                .map(u -> EntityModel.of(usuarioService.toResponse(u),
                        Link.of(ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{id}")
                                .buildAndExpand(u.getId())
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

    @Operation(summary = "Save a new user")
    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<EntityModel<UsuarioResponse>> save(@RequestBody @Valid UsuarioRequest r) {
        var entity = usuarioService.toEntity(r);
        var saved = usuarioService.save(entity);
        var response = usuarioService.toResponse(saved);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(saved.getId())
                        .toUriString())
                .withSelfRel();

        var model = EntityModel.of(response, selfLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @Operation(summary = "Find a user by ID")
    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<EntityModel<UsuarioResponse>> findById(@PathVariable Long id) {
        var entity = usuarioService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = usuarioService.toResponse(entity);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .build()
                        .toUriString())
                        .withSelfRel();

        return ResponseEntity.ok(EntityModel.of(response, selfLink));
    }

    @Operation(summary = "Get user points by ID")
    @GetMapping(value = "/{id}/pontos")
    public ResponseEntity<Integer> getPontos(@PathVariable Long id) {
        var pontos = quizService.sumPointsByUsuarioId(id);
        if (pontos == null) {
            pontos = 0;
        }
        return ResponseEntity.ok(pontos);
    }
}
