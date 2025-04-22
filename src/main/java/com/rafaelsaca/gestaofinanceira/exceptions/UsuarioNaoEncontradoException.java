package com.rafaelsaca.gestaofinanceira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UsuarioNaoEncontradoException extends AppException {
  
    @Override
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Verifique os dados!");
        problemDetail.setDetail("Usuário não encontrado!");

        return problemDetail;
    }

}
