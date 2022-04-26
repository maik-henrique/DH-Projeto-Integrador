package br.com.meli.dhprojetointegrador.exception;

import java.io.Serializable;
import java.util.function.Supplier;

public class BuyerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4870885287350391078L;

    public BuyerNotFoundException(String message) {
        super(message);
    }

}
