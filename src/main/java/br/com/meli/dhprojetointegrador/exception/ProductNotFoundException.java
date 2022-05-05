package br.com.meli.dhprojetointegrador.exception;

public class ProductNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -4870885287350391078L;

    public ProductNotFoundException(String message) {
        super(message);
    }
}
