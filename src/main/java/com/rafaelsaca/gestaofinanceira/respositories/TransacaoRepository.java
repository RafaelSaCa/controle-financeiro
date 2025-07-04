package com.rafaelsaca.gestaofinanceira.respositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT COALESCE(SUM(t.valor), 0.00) FROM Transacao t WHERE t.usuario.id = :usuarioId AND t.tipo = 'RECEITA'")
    BigDecimal totalReceitas(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COALESCE(SUM(t.valor), 0.00) FROM Transacao t WHERE t.usuario.id = :usuarioId AND t.tipo = 'DESPESA'")
    BigDecimal totalDespesas(@Param("usuarioId") Long usuarioId);

    // //Combinação: buscar por usuário, tipo e data no intervalo
    // List<Transacao> findByUsuarioIdAndTipoAndDataTransacaoBetween(
    // Long usuarioId,
    // TipoTransacao tipo,
    // LocalDate inicio,
    // LocalDate fim
    // );

    @Query(" SELECT t FROM Transacao t WHERE t.usuario.id = :usuarioId AND (:tipo IS NULL OR t.tipo = :tipo) AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)")
    List<Transacao> filtrarTransacoes( @Param("usuarioId") Long usuarioId, @Param("tipo") TipoTransacao tipo, @Param("categoriaId") Long categoriaId);

    @Query("SELECT COALESCE(SUM(t.valor), 0.00) FROM Transacao t WHERE t.usuario.id = :usuarioId AND (:tipo IS NULL OR t.tipo = :tipo) AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)")
    BigDecimal totalFiltrado(@Param("usuarioId") Long usuarioId, @Param("tipo") TipoTransacao tipo,
            @Param("categoriaId") Long categoriaId);

    @Query("SELECT COALESCE(SUM(t.valor), 0.00) FROM Transacao t WHERE t.usuario.id = :usuarioId AND t.tipo = 'RECEITA' AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)")
    BigDecimal totalReceitasFiltrado(@Param("usuarioId") Long usuarioId, @Param("categoriaId") Long categoriaId);

    @Query("""
                SELECT COALESCE(SUM(t.valor), 0.00)
                FROM Transacao t
                WHERE t.usuario.id = :usuarioId
                  AND t.tipo = 'DESPESA'
                  AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)
            """)
    BigDecimal totalDespesasFiltrado(@Param("usuarioId") Long usuarioId, @Param("categoriaId") Long categoriaId);

}
