package br.com.meli.dhprojetointegrador.exception.handler;

import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadDTO;
import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadResponse;
import br.com.meli.dhprojetointegrador.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Used to catch and return the proper response payload for exceptions thrown at the layers bellow the controllers.
 */
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	/**
	 * @param exception BusinessValidator thrown at the service layer
	 * @return ExceptionPayloadResponse filled with the exception data
	 */
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

	/**
	 * Generates payload response for exceptions related to field validation on request bodies
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();

		Map<String, String> errorsMap = bindingResult
				.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

		ExceptionPayloadResponse exceptionPayload = ExceptionPayloadResponse.builder()
				.timestamp(LocalDateTime.now())
				.title("Some fields are missing in the request payload")
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.fieldToMessageMap(errorsMap)
				.description(String.format("A total of %d fields are missing", bindingResult.getErrorCount()))
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = {PurchaseOrderNotFoundException.class})
	protected ResponseEntity<Object> handlePurchaseOrderNotFoundException(PurchaseOrderNotFoundException exception) {
		ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
				.timestamp(LocalDateTime.now())
				.title("PurchaseOrder Not Found")
				.statusCode(HttpStatus.NOT_FOUND.value())
				.description(exception.getMessage())
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
	}

	/**
	 * Author: Bruno Mendes
	 * Method: handleBuyerNotFoundException
	 * Description: Handler para a exeption buyer not found
	 */
	@ExceptionHandler(value = {BuyerNotFoundException.class})
	protected ResponseEntity<Object> handleBuyerNotFoundException(BuyerNotFoundException exception) {
		ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
				.timestamp(LocalDateTime.now())
				.title("Buyer Not Found")
				.statusCode(HttpStatus.NOT_FOUND.value())
				.description(exception.getMessage())
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
	}



	/**
	 * Author: Bruno Mendes
	 * Method: handleNotEnoughProductsException
	 * Description: Handler para a exeption not enough products
	 */
	@ExceptionHandler(value = {NotEnoughProductsException.class})
	protected ResponseEntity<Object> handleNotEnoughProductsException(NotEnoughProductsException exception) {
		ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
				.timestamp(LocalDateTime.now())
				.title("Not Enough Products")
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.description(exception.getMessage())
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Author: Bruno Mendes
	 * Method: handleProductNotFoundException
	 * Description: Handler para a exeption Product Not Found
	 */
	@ExceptionHandler(value = {ProductNotFoundException.class})
	protected ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
		ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
				.timestamp(LocalDateTime.now())
				.title("Product Not Found")
				.statusCode(HttpStatus.NOT_FOUND.value())
				.description(exception.getMessage())
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
	}
}