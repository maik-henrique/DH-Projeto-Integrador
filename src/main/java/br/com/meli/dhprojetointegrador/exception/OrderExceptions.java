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
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(exceptionPayload, HttpStatus.BAD_REQUEST);
    }
}
