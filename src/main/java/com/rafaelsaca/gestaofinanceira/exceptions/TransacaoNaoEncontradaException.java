package com.rafaelsaca.gestaofinanceira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransacaoNaoEncontradaException extends AppException {

    @Override
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Transações não encontradas!");
        problemDetail.setDetail("Não existe nenhuma transação para esse usuário!");

        return problemDetail;
    }
}
