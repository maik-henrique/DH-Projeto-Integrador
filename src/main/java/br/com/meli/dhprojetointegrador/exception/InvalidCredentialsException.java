package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends AuthException {
    private static final long serialVersionUID = 723123123123L;

    private static final String EXCEPTION_TITLE = "Invalid Credentials";
    private static final String EXCEPTION_MESSAGE = "The provided credentials are invalid";
    private static final HttpStatus EXCEPTION_STATUS = HttpStatus.UNAUTHORIZED;

    public InvalidCredentialsException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TITLE, EXCEPTION_STATUS);
    }

}
