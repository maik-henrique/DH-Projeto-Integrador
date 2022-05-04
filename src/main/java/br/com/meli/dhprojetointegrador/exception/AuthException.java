package br.com.meli.dhprojetointegrador.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AuthException extends RuntimeException {
    private static final long serialVersionUID = -2641662338043073290L;
    private String title;
    private String message;
    private HttpStatus httpStatus;

    public AuthException(String message, String title, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.title = title;
        this.httpStatus = httpStatus;
    }
}
