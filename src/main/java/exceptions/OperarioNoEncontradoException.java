package exceptions;

public class OperarioNoEncontradoException extends Exception {
    public OperarioNoEncontradoException() {
    }

    public OperarioNoEncontradoException(String message) {
        super(message);
    }
}
