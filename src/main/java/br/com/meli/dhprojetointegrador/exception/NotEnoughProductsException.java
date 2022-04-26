package br.com.meli.dhprojetointegrador.exception;

/**
 * Author: Bruno Mendes
 * Method: NotEnoughProductsException
 * Description: Exception personalizada para quando um produto não tiver estoque suficiente
 */

public class NotEnoughProductsException extends RuntimeException{
    private static final long serialVersionUID = -4870885287350391078L;

    public NotEnoughProductsException(String message) {
        super(message);
    }
}
