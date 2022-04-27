package br.com.meli.dhprojetointegrador.exception;

import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OrderExceptions extends ResponseEntityExceptionHandler {

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
}
