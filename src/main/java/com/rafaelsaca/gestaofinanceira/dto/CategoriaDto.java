package com.rafaelsaca.gestaofinanceira.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(@NotBlank String descricao) {
}
 