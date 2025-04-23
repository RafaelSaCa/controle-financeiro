package com.rafaelsaca.gestaofinanceira.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDto(@NotBlank String descricao) {
}
