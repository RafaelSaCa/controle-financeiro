package com.rafaelsaca.gestaofinanceira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // permite todos os endpoints
                        .allowedOrigins("http://localhost:4200") // permite o Angular local
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // métodos permitidos
                        .allowedHeaders("*") // permite todos os headers
                        .allowCredentials(true); // se quiser permitir cookies/autenticação
            }
        };
    }
}