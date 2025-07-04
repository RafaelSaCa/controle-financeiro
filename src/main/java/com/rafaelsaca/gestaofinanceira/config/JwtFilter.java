package com.rafaelsaca.gestaofinanceira.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rafaelsaca.gestaofinanceira.models.Usuario;
import com.rafaelsaca.gestaofinanceira.services.JwtService;
import com.rafaelsaca.gestaofinanceira.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public JwtFilter(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // // Pular validação de token para rotas públicas
        // String path = request.getRequestURI();
        // if (path.startsWith("/auth")) {
        //     filterChain.doFilter(request, response);
        //     return;
        // }

         // Permitir requisições preflight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // Verificação do token para rotas protegidas
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Usuario usuario = (Usuario)usuarioService.loadUserByUsername(email);

                if (jwtService.isTokenValid(token, usuario)) {
                    UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
