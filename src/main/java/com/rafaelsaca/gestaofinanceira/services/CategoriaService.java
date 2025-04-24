package com.rafaelsaca.gestaofinanceira.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rafaelsaca.gestaofinanceira.dto.CategoriaDTO;
import com.rafaelsaca.gestaofinanceira.exceptions.CategoriaExistenteException;
import com.rafaelsaca.gestaofinanceira.exceptions.CategoriaNaoEncontradaException;
import com.rafaelsaca.gestaofinanceira.mappers.CategoriaMapper;
import com.rafaelsaca.gestaofinanceira.models.Categoria;
import com.rafaelsaca.gestaofinanceira.respositories.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria cadastrar(CategoriaDTO dto) {
        Optional<Categoria> categoriaExistente = repository.findByDescricaoIgnoreCase(dto.descricao());

        if (categoriaExistente.isPresent()) {
            throw new CategoriaExistenteException();
        }

        Categoria categoria = CategoriaMapper.toModel(dto);

        return repository.save(categoria);

    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        Optional<Categoria> categoriaExistente = repository.findByDescricaoIgnoreCase(dto.descricao());

        if (categoriaExistente.isPresent() && !categoriaExistente.get().getId().equals(id)) {
            throw new CategoriaExistenteException();
        }
        categoria.setDescricao(dto.descricao());

        return repository.save(categoria);

    }

    public void remover(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException());

        repository.delete(categoria);
    }

}
