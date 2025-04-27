package com.rafaelsaca.gestaofinanceira.dto;

import java.math.BigDecimal;
import java.util.List;

import com.rafaelsaca.gestaofinanceira.models.Transacao;

public record TransacaoResponse(List<Transacao> transacoes,
                                BigDecimal totalReceitas,
                                BigDecimal totalDespesas,
                                BigDecimal saldo) {

 

    //método static factory auxiliar  -já que os records não permitem construtores explícitos tradicionais como nas classes comuns.
    public static TransacaoResponse of(List<Transacao> transacoes, BigDecimal totalReceitas,
            BigDecimal totalDespesas, BigDecimal saldo) {
        return new TransacaoResponse(transacoes, totalReceitas, totalDespesas,saldo);
    }


}
