package com.rafaelsaca.gestaofinanceira.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(@NotBlank String nome,
                         @NotBlank String sobrenome,
                         @NotBlank String email,
                         @NotBlank String senha) {

}
