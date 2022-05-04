package br.com.meli.dhprojetointegrador.exception;

public class PurchaseOrderNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -4870885287350391078L;

    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }
}
