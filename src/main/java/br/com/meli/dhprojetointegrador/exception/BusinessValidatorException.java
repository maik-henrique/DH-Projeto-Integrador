package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

public class BusinessValidatorException extends RuntimeException {

    private static final long serialVersionUID = -5958260919469224826L;
    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final String ERROR_TITLE = "Ocorreu um erro durante a validação";

    public BusinessValidatorException(String message) {
        super(message);
    }

}
