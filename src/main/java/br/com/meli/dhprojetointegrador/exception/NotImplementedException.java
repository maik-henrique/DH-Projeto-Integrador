package br.com.meli.dhprojetointegrador.exception;

public class NotImplementedException extends RuntimeException {
    private static final long serialVersionUID = -142832191860931564L;

    public NotImplementedException() {
        super("Implementation is pending, so it wasn`t possible to execute");
    }
}
