package com.rafaelsaca.gestaofinanceira.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsaca.gestaofinanceira.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

    Optional<Categoria> findByDescricaoIgnoreCase(String descricao);
}
