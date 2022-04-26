package br.com.meli.dhprojetointegrador.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadResponse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	   @ExceptionHandler(BusinessValidatorException.class)
	    protected ResponseEntity<?> handleAuthException(BusinessValidatorException exception) {
			ExceptionPayloadResponse exceptionPayload = ExceptionPayloadResponse.builder()
					.timestamp(LocalDateTime.now())
					.title(exception.getErrorTitle())
					.statusCode(exception.getHttpStatus().value())
					.description(exception.getMessage())
					.build();

			return new ResponseEntity<>(exceptionPayload, exception.getHttpStatus());
		}

}
