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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelsaca.gestaofinanceira.dto.TransacaoDTO;
import com.rafaelsaca.gestaofinanceira.dto.TransacaoResponse;
import com.rafaelsaca.gestaofinanceira.models.Transacao;
import com.rafaelsaca.gestaofinanceira.services.TransacaoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Transacao> cadastrar(@RequestBody @Valid TransacaoDTO dto) {
        Transacao transacao = service.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @GetMapping()
    public ResponseEntity<List<Transacao>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // @GetMapping("/usuario/{id}")
    // public ResponseEntity<List<Transacao>> buscaTransacoesPorUsuario(@PathVariable Long id) {
    //     List<Transacao> transacoes = service.buscaTransacoesPorUsuario(id);

    //     return ResponseEntity.ok(transacoes);
    // }

    @GetMapping("/usuario")
    public ResponseEntity<TransacaoResponse> transacoesPorUsuario(@RequestParam @NotNull Long usuarioId) {
        TransacaoResponse transacoes = service.transacoesPorUsuario(usuarioId);

        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<Transacao>> buscaPorTipo(@RequestParam String tipo) {
        List<Transacao> transacoes = service.buscaPorTipo(tipo);

        return ResponseEntity.ok(transacoes);
    }

    @PutMapping("/{transacaoId}")
    public ResponseEntity<Transacao> atualizar(@PathVariable  @NotNull Long transacaoId,
            @RequestBody @Valid TransacaoDTO dto) {

        return ResponseEntity.ok(service.atualizar(transacaoId, dto));
    }

    @DeleteMapping("/{transacaoId}")
    public ResponseEntity<Void> deletar (@PathVariable @NotNull Long transacaoId){
        service.deletar(transacaoId);
        return ResponseEntity.noContent().build();
    }

}
