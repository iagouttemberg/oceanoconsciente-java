package com.example.oceanoconsciente.service;

import com.example.oceanoconsciente.dto.request.UsuarioRequest;
import com.example.oceanoconsciente.dto.response.UsuarioResponse;
import com.example.oceanoconsciente.entity.Usuario;
import com.example.oceanoconsciente.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Override
    public Collection<Usuario> findAll(Example<Usuario> example) {
        return usuarioRepository.findAll(example);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        return usuarioRepository.save(e);
    }

    @Override
    public Usuario toEntity(UsuarioRequest dto) {

        var endereco = enderecoService.toEntity(dto.endereco());

        return Usuario.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .senha(dto.senha())
                .endereco(endereco)
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {

        var endereco = enderecoService.toResponse(e.getEndereco());

        return UsuarioResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .cpf(e.getCpf())
                .email(e.getEmail())
                .senha(e.getSenha())
                .endereco(endereco)
                .build();
    }
}
