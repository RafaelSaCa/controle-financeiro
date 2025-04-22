package com.rafaelsaca.gestaofinanceira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AppException extends RuntimeException{

    
    public ProblemDetail toProblemDetail(){
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problemDetail.setTitle("Erro interno no servidor da aplicação!");
        
        return problemDetail;
}
}
