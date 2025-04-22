package com.rafaelsaca.gestaofinanceira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EmailJaCadastradoException extends AppException {
    private String email;

    public EmailJaCadastradoException(String email) {
        this.email = email;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        problemDetail.setTitle("Verifique os dados!");
        problemDetail.setDetail("Já existe um usuário cadastrado com esse e-mail: " + email);

        return problemDetail;
    }

}
