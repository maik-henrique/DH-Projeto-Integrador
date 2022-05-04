package br.com.meli.dhprojetointegrador.exception;

public class NotfoundException extends RuntimeException {

    private static final long serialVersionUID = -4870885287350391078L;
    public NotfoundException(String message) {
        super(message);
    }
}
