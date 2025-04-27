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

    public Transacao cadastrar(TransacaoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        Transacao transacao = transacaoMapper.toModel(dto);

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

    public TransacaoResponse transacoesPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        List<Transacao> transacoes = repository.findByUsuarioId(usuario.getId());


        BigDecimal totalReceitas = repository.totalReceitas(usuario.getId());
        BigDecimal totalDespesas = repository.totalDespesas(usuario.getId());
        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return TransacaoResponse.of(transacoes, totalReceitas, totalDespesas,saldo);
    }

}
