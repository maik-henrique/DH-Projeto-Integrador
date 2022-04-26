package br.com.meli.dhprojetointegrador.exception;

/**
 * Author: Bruno Mendes
 * Method: BuyerNotFoundException
 * Description: Exception personalizada para quando um comprador não for encontrado
 */

public class BuyerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4870885287350391078L;

    public BuyerNotFoundException(String message) {
        super(message);
    }

}
