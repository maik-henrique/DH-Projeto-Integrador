package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessValidatorException {
	private static final long serialVersionUID = -4851963337358236626L;
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String ERROR_TITLE = "The target resource wasn't found";
    private static final String DEFAULT_MESSAGE = "The server wasn't able to locate the fetched resource";
	
	public ResourceNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
	
	public ResourceNotFoundException(String message) {
		super(message, HTTP_STATUS, ERROR_TITLE);
	}
}
