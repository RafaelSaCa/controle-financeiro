package com.rafaelsaca.gestaofinanceira.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rafaelsaca.gestaofinanceira.exceptions.AppException;

@RestControllerAdvice
public class GlobalExceptionHandler {

           @ExceptionHandler(AppException.class)
        public ProblemDetail handleAppException(AppException e){
                return e.toProblemDetail();
        }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ProblemDetail handleMethodArgumentNotValidException( MethodArgumentNotValidException e){
                
                var fieldErrors = e.getFieldErrors()
                        .stream()
                        .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                        .toList();

                var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

                problemDetail.setDetail("Os dados enviados não são válidos!");
                problemDetail.setProperty("parametros-invalidos",  fieldErrors);

                return problemDetail;
        }

        private record InvalidParam(String campo, String motivo){}

}
