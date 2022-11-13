package exceptions;

public class PromocionNoEncontradaException extends Exception{
    public PromocionNoEncontradaException() {
    }

    public PromocionNoEncontradaException(String message) {
        super(message);
    }
}
