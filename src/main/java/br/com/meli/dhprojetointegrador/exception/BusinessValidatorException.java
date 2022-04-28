package br.com.meli.dhprojetointegrador.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Base exception for business validation errors
 */
@Getter
public class BusinessValidatorException extends RuntimeException {

    private static final long serialVersionUID = -6619873984883570116L;
    private final HttpStatus httpStatus;
    private final String errorTitle ;

    public BusinessValidatorException(String message) {
    	super(message);
    	this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    	this.errorTitle = "An error occurred during business validation processing";
    }

	public BusinessValidatorException(String message, HttpStatus httpStatus, String errorTitle) {
		super(message);
		this.httpStatus = httpStatus;
		this.errorTitle = errorTitle;		
	}
    
    

    
}
