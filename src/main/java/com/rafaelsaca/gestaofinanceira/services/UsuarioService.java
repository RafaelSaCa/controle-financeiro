package com.rafaelsaca.gestaofinanceira.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rafaelsaca.gestaofinanceira.dto.UsuarioDTO;
import com.rafaelsaca.gestaofinanceira.exceptions.EmailJaCadastradoException;
import com.rafaelsaca.gestaofinanceira.exceptions.UsuarioNaoEncontradoException;
import com.rafaelsaca.gestaofinanceira.mappers.UsuarioMapper;
import com.rafaelsaca.gestaofinanceira.models.Usuario;
import com.rafaelsaca.gestaofinanceira.respositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;

    }

    public Usuario cadastrar(UsuarioDTO dto) {
        Optional<Usuario> emailExistente = repository.findByEmail(dto.email());

        if (emailExistente.isPresent()) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Usuario usuario = UsuarioMapper.toModel(dto);
        //encriptografa a senha
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
     


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
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return repository.save(usuario);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado!"));
    }

}
