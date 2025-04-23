package com.rafaelsaca.gestaofinanceira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CategoriaExistenteException extends AppException{

    
    @Override
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        problemDetail.setTitle("Verifique os dados!");
        problemDetail.setDetail("Categoria já existe!");

        return problemDetail;
    }



}
