package com.rafaelsaca.gestaofinanceira.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rafaelsaca.gestaofinanceira.dto.TransacaoDTO;
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

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository repository, CategoriaRepository categoriaRepository,
            UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Transacao cadastrar(TransacaoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        Transacao transacao = TransacaoMapper.toModel(dto);

        return repository.save(transacao);
    }

    public List<Transacao> listar() {
        return repository.findAll();
    }

    public List<Transacao> buscaTransacoesPorUsuario(Long usuarioId) {
        List<Transacao> transacoes = repository.findByUsuarioId(usuarioId);

        if (transacoes.isEmpty()) {
            throw new TransacaoNaoEncontradaException();
        }

        return transacoes;
    }

    public List<Transacao> buscaPorTipo(String tipo) {
        TipoTransacao tipoTransacao = TipoTransacao.valueOf(tipo.toUpperCase());
        List<Transacao> transacoes = repository.findByTipo(tipoTransacao);

        if (transacoes.isEmpty()) {
            throw new TransacaoNaoEncontradaException();
        }

        return transacoes;
    }
}
