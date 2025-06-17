package com.rafaelsaca.gestaofinanceira.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelsaca.gestaofinanceira.dto.TransacaoDTO;
import com.rafaelsaca.gestaofinanceira.dto.TransacaoResponse;
import com.rafaelsaca.gestaofinanceira.models.Transacao;
import com.rafaelsaca.gestaofinanceira.models.Usuario;
import com.rafaelsaca.gestaofinanceira.services.TransacaoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping("/transacoes")
    public ResponseEntity<Transacao> cadastrar(@RequestBody @Valid TransacaoDTO dto) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Transacao transacao = service.cadastrar(dto, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    // @GetMapping("/transacoes")
    // public ResponseEntity<List<Transacao>> listar() {
    // return ResponseEntity.ok(service.listar());
    // }

    @GetMapping("/transacoes")
    public ResponseEntity<TransacaoResponse> transacaoPorUsuario(@RequestParam(required = false) String tipo,
            @RequestParam(required = false) Long categoriaId) {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long usuarioId = usuario.getId();

        TransacaoResponse transacao = service.transacoesPorUsuario(usuarioId, tipo, categoriaId);

        return ResponseEntity.ok(transacao);
    }

    // @GetMapping("/transacoes/tipo")
    // public ResponseEntity<List<Transacao>> buscaPorTipo(@RequestParam String tipo) {
    //     List<Transacao> transacoes = service.buscaPorTipo(tipo);

    //     return ResponseEntity.ok(transacoes);
    // }

    @PutMapping("/transacoes/{transacaoId}")
    public ResponseEntity<Transacao> atualizar(@PathVariable @NotNull Long transacaoId,
            @RequestBody @Valid TransacaoDTO dto) {

        return ResponseEntity.ok(service.atualizar(transacaoId, dto));
    }

    @DeleteMapping("/transacoes/{transacaoId}")
    public ResponseEntity<Void> deletar(@PathVariable @NotNull Long transacaoId) {
        service.deletar(transacaoId);
        return ResponseEntity.noContent().build();
    }

}
