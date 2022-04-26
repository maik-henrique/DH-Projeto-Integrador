package br.com.meli.dhprojetointegrador.exception;

public class NotEnoughProductsException extends RuntimeException{
    private static final long serialVersionUID = -4870885287350391078L;

    public NotEnoughProductsException(String message) {
        super(message);
    }
}
