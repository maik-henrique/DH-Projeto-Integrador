package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Base exception for business validation errors
 */
@Getter
public class BusinessValidatorException extends RuntimeException {

    private static final long serialVersionUID = -6619873984883570116L;
    private final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    private final String errorTitle = "An error occurred during business validation processing";

    public BusinessValidatorException(String message) {
        super(message);
    }

}
