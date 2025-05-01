package com.rafaelsaca.gestaofinanceira.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelsaca.gestaofinanceira.dto.LoginDTO;
import com.rafaelsaca.gestaofinanceira.dto.TokenDTO;
import com.rafaelsaca.gestaofinanceira.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        String token = authService.login(loginDTO.email(), loginDTO.senha());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
