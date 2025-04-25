package com.rafaelsaca.gestaofinanceira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransacaoDTO( @NotBlank String descricao,
                            @NotNull @Positive BigDecimal valor,
                            @NotBlank String tipo, 
                            @NotNull Long categoriaId,
                            @NotNull Long usuarioId,
                            @NotNull LocalDate dataVencimento) {

}
