package com.rafaelsaca.gestaofinanceira.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.rafaelsaca.gestaofinanceira.exceptions.AppException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        //retornar erros com um formato mais padronizado, estilo RFC 7807 
        //(Problem Details for HTTP APIs)

        @ExceptionHandler(AppException.class)
        public ProblemDetail handleAppException(AppException e) {
                return e.toProblemDetail();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

                var fieldErrors = e.getFieldErrors()
                                .stream()
                                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                                .toList();

                var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

                problemDetail.setDetail("Os dados enviados não são válidos!");
                problemDetail.setProperty("parametros-invalidos", fieldErrors);

                return problemDetail;
        }

        private record InvalidParam(String campo, String motivo) {
        }

        //verifica se enviar um ID inválido (tipo string e nao long)
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ProblemDetail> handleInvalidPathParam(MethodArgumentTypeMismatchException ex,
                        HttpServletRequest request) {
                ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                problem.setTitle("Verifique os dados!");
                problem.setDetail("O tipo do parâmetro é inválido: " + ex.getName());
                problem.setInstance(URI.create(request.getRequestURI()));
                return ResponseEntity.badRequest().body(problem);
        }

}
