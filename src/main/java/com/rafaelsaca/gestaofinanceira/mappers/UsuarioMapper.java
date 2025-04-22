package com.rafaelsaca.gestaofinanceira.mappers;

import org.springframework.stereotype.Component;

import com.rafaelsaca.gestaofinanceira.dto.UsuarioDTO;
import com.rafaelsaca.gestaofinanceira.models.Usuario;

@Component
public class UsuarioMapper {

    
    public static Usuario toModel(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());

        return usuario;
    }

    public static UsuarioDTO dto (Usuario usuario){
        return new UsuarioDTO(
            usuario.getNome(),
            usuario.getSobrenome(),
            usuario.getEmail(),
            usuario.getSenha()
        );
    }
  

    

}
