package exceptions;

public class ComandaNoEncontradaException extends Exception{
    public ComandaNoEncontradaException() {
    }

    public ComandaNoEncontradaException(String message) {
        super(message);
    }
}
