package exceptions;

public class MesaNoEncontradaException extends Exception{
    public MesaNoEncontradaException() {
    }

    public MesaNoEncontradaException(String message) {
        super(message);
    }
}
