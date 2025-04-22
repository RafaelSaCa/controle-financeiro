package com.rafaelsaca.gestaofinanceira.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rafaelsaca.gestaofinanceira.dto.UsuarioDTO;
import com.rafaelsaca.gestaofinanceira.exceptions.EmailJaCadastradoException;
import com.rafaelsaca.gestaofinanceira.exceptions.UsuarioNaoEncontradoException;
import com.rafaelsaca.gestaofinanceira.mappers.UsuarioMapper;
import com.rafaelsaca.gestaofinanceira.models.Usuario;
import com.rafaelsaca.gestaofinanceira.respositories.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrar(UsuarioDTO dto) {
        Optional<Usuario> emailExistente = repository.findByEmail(dto.email());

        if (emailExistente.isPresent()) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Usuario usuario = UsuarioMapper.toModel(dto);

        return repository.save(usuario);

    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario atualizar(Long id, UsuarioDTO dto) {

        Usuario usuario = repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());

        Optional<Usuario> usuarioComMesmoEmail = repository.findByEmail(dto.email());

        if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getId().equals(usuario.getId())) {
            throw new EmailJaCadastradoException(dto.email());
        }

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());

        return repository.save(usuario);

    }
}
