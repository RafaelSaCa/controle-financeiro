package com.rafaelsaca.gestaofinanceira.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelsaca.gestaofinanceira.dto.CategoriaDto;
import com.rafaelsaca.gestaofinanceira.models.Categoria;
import com.rafaelsaca.gestaofinanceira.services.CategoriaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Categoria> cadastrar(@RequestBody @Valid CategoriaDto dto) {
        Categoria categoria = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping()
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok().body(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaDto dto) {
        Categoria categoria = service.atualizar(id, dto);

        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();

    }

}
