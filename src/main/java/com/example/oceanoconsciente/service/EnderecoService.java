package com.example.oceanoconsciente.service;

import com.example.oceanoconsciente.dto.request.EnderecoRequest;
import com.example.oceanoconsciente.dto.response.EnderecoResponse;
import com.example.oceanoconsciente.entity.Endereco;
import com.example.oceanoconsciente.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EnderecoService implements ServiceDTO<Endereco, EnderecoRequest, EnderecoResponse> {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Collection<Endereco> findAll(Example<Endereco> example) {
        return enderecoRepository.findAll(example);
    }

    @Override
    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElse(null);
    }

    @Override
    public Endereco save(Endereco e) {
        return enderecoRepository.save(e);
    }

    @Override
    public Endereco toEntity(EnderecoRequest dto) {
        return Endereco.builder()
                .cep(dto.cep())
                .cidade(dto.cidade())
                .rua(dto.rua())
                .numero(dto.numero())
                .complemento(dto.complemento())
                .build();

    }

    @Override
    public EnderecoResponse toResponse(Endereco e) {

        return EnderecoResponse.builder()
                .id(e.getId())
                .cep(e.getCep())
                .cidade(e.getCidade())
                .rua(e.getRua())
                .numero(e.getNumero())
                .complemento(e.getComplemento())
                .build();
    }
}
