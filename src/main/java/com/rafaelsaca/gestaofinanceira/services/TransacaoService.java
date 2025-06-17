package com.rafaelsaca.gestaofinanceira.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rafaelsaca.gestaofinanceira.dto.TransacaoDTO;
import com.rafaelsaca.gestaofinanceira.dto.TransacaoResponse;
import com.rafaelsaca.gestaofinanceira.exceptions.CategoriaNaoEncontradaException;
import com.rafaelsaca.gestaofinanceira.exceptions.TransacaoNaoEncontradaException;
import com.rafaelsaca.gestaofinanceira.exceptions.UsuarioNaoEncontradoException;
import com.rafaelsaca.gestaofinanceira.mappers.TransacaoMapper;
import com.rafaelsaca.gestaofinanceira.models.Categoria;
import com.rafaelsaca.gestaofinanceira.models.TipoTransacao;
import com.rafaelsaca.gestaofinanceira.models.Transacao;
import com.rafaelsaca.gestaofinanceira.models.Usuario;
import com.rafaelsaca.gestaofinanceira.respositories.CategoriaRepository;
import com.rafaelsaca.gestaofinanceira.respositories.TransacaoRepository;
import com.rafaelsaca.gestaofinanceira.respositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private TransacaoMapper transacaoMapper;

    public TransacaoService(TransacaoRepository repository, CategoriaRepository categoriaRepository,
            TransacaoMapper transacaoMapper,
            UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.transacaoMapper = transacaoMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public Transacao cadastrar(TransacaoDTO dto, Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        Transacao transacao = transacaoMapper.toModel(dto, usuario);
        transacao.setCategoria(categoria);

        return repository.save(transacao);
    }

    public List<Transacao> listar() {
        return repository.findAll();
    }

    @Transactional
    public Transacao atualizar(Long transacaoId, TransacaoDTO dto) {
        Transacao transacao = repository.findById(transacaoId)
                .orElseThrow(() -> new TransacaoNaoEncontradaException());

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(TipoTransacao.valueOf(dto.tipo().toUpperCase()));
        transacao.setDataVencimento(dto.dataVencimento());

        transacao.setCategoria(categoria);

        return repository.save(transacao);
    }

    @Transactional
    public void deletar(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException());

        repository.delete(transacao);
    }

    // public List<Transacao> buscaTransacoesPorUsuario(Long usuarioId) {
    // List<Transacao> transacoes = repository.findByUsuarioId(usuarioId);

    // if (transacoes.isEmpty()) {
    // throw new TransacaoNaoEncontradaException();
    // }

    // return transacoes;
    // }

    public List<Transacao> buscaPorTipo(String tipo) {
        TipoTransacao tipoTransacao = TipoTransacao.valueOf(tipo.toUpperCase());
        List<Transacao> transacoes = repository.findByTipo(tipoTransacao);

        if (transacoes.isEmpty()) {
            throw new TransacaoNaoEncontradaException();
        }

        return transacoes;
    }


    public TransacaoResponse transacoesPorUsuario(Long usuarioId, String tipoStr, Long categoriaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        TipoTransacao tipo = null;
        if (tipoStr != null && !tipoStr.isBlank()) {
            try {
                tipo = TipoTransacao.valueOf(tipoStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Tipo inválido: " + tipoStr);
            }
        }

         List<Transacao> transacoes = repository.filtrarTransacoes(usuario.getId(), tipo, categoriaId);

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        if (tipo == TipoTransacao.RECEITA) {
            // Filtrado só por RECEITA → Calcula só receitas
            totalReceitas = repository.totalReceitasFiltrado(usuarioId, categoriaId);

        } else if (tipo == TipoTransacao.DESPESA) {
            // Filtrado só por DESPESA → Calcula só despesas
            totalDespesas = repository.totalDespesasFiltrado(usuarioId, categoriaId);

        } else {
            // Sem filtro de tipo → Calcula os dois
            totalReceitas = repository.totalReceitasFiltrado(usuarioId, categoriaId);
            totalDespesas = repository.totalDespesasFiltrado(usuarioId, categoriaId);
        }

        BigDecimal saldo = repository.totalReceitas(usuarioId)
                .subtract(repository.totalDespesas(usuarioId));

        return TransacaoResponse.of(transacoes, totalReceitas, totalDespesas, saldo);
    }

}
