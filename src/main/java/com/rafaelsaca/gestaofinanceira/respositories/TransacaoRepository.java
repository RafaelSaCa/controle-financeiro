package com.rafaelsaca.gestaofinanceira.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsaca.gestaofinanceira.models.TipoTransacao;
import com.rafaelsaca.gestaofinanceira.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    
    // // Buscar por descrição exata (case-sensitive)
    // List<Transacao> findByDescricao(String descricao);

    // // Buscar por descrição contendo texto (like %texto%) — case-insensitive
    // List<Transacao> findByDescricaoContainingIgnoreCase(String termo);

    // // Buscar por valor maior que X
    // List<Transacao> findByValorGreaterThan(BigDecimal valor);

    // // Buscar por data de transação entre duas datas
    // List<Transacao> findByDataTransacaoBetween(LocalDate inicio, LocalDate fim);

    // // Buscar por vencimento menor que hoje (vencidas)
    // List<Transacao> findByDataVencimentoBefore(LocalDate hoje);

    // Buscar por tipo (assumindo que seja Enum: DESPESA ou RECEITA)
    List<Transacao> findByTipo(TipoTransacao tipo);

    // // Buscar por categoria
    // List<Transacao> findByCategoriaId(Long categoriaId);

    // Buscar por usuário
    List<Transacao> findByUsuarioId(Long usuarioId);

    // //Combinação: buscar por usuário, tipo e data no intervalo
    // List<Transacao> findByUsuarioIdAndTipoAndDataTransacaoBetween(
    //     Long usuarioId,
    //     TipoTransacao tipo,
    //     LocalDate inicio,
    //     LocalDate fim
    // );

}
