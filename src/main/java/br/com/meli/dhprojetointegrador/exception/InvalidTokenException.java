package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends AuthException {
    private static final long serialVersionUID = 123123123123L;

    private static final String EXCEPTION_MESSAGE = "The provided token is invalid";
    private static final String EXCEPTION_TITLE = "The provided token is invalid";
    private static final HttpStatus EXCEPTION_STATUS = HttpStatus.UNAUTHORIZED;

    public InvalidTokenException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TITLE, EXCEPTION_STATUS);
    }
}
