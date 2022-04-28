package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends BusinessValidatorException {
	private static final long serialVersionUID = -4851963337358236626L;
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String ERROR_TITLE = "The target resource wasn't found";
    private static final String DEFAULT_MESSAGE = "The server wasn't able to locate the fetched resource";
	
	public ResourceNotFound() {
		super(DEFAULT_MESSAGE);
	}
	
	public ResourceNotFound(String message) {
		super(message, HTTP_STATUS, ERROR_TITLE);
	}
}
