package com.example.oceanoconsciente.resource;

import com.example.oceanoconsciente.dto.request.EnderecoRequest;
import com.example.oceanoconsciente.dto.response.EnderecoResponse;
import com.example.oceanoconsciente.entity.Endereco;
import com.example.oceanoconsciente.service.EnderecoService;
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
@RequestMapping(value = "/enderecos")
public class EnderecoResource implements ResourceDTO<EnderecoRequest, EnderecoResponse> {
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Get all addresses")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EnderecoResponse>>> findAll(

            @RequestParam(name = "cep", required = false) String cep,
            @RequestParam(name = "cidade", required = false) String cidade,
            @RequestParam(name = "rua", required = false) String rua,
            @RequestParam(name = "numero", required = false) String numero,
            @RequestParam(name = "complemento", required = false) String complemento

    ) {
        var endereco = Endereco.builder()
                .cep(cep)
                .cidade(cidade)
                .rua(rua)
                .numero(numero)
                .complemento(complemento)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Endereco> example = Example.of(endereco, matcher);
        Collection<Endereco> enderecos = enderecoService.findAll(example);

        var response = enderecos.stream()
                .map(e -> EntityModel.of(enderecoService.toResponse(e),
                        Link.of(ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{id}")
                                .buildAndExpand(e.getId())
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

    @Operation(summary = "Save a new address")
    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<EntityModel<EnderecoResponse>> save(@RequestBody @Valid EnderecoRequest r) {
        var entity = enderecoService.toEntity(r);
        var saved = enderecoService.save(entity);
        var response = enderecoService.toResponse(saved);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(saved.getId())
                        .toUriString())
                .withSelfRel();

        var model = EntityModel.of(response, selfLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @Operation(summary = "Find an address by ID")
    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<EntityModel<EnderecoResponse>> findById(@PathVariable Long id) {
        var entity = enderecoService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = enderecoService.toResponse(entity);

        var selfLink = Link.of(ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .build()
                        .toUriString())
                .withSelfRel();

        return ResponseEntity.ok(EntityModel.of(response, selfLink));
    }
}
