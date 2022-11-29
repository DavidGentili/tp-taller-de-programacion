package exceptions.controlador;

/**
 * Excepcion emitida cuando hubo un error con una promocion
 */
public class ErrorPromocionException extends Exception{
    public ErrorPromocionException() {
    }

    public ErrorPromocionException(String message) {
        super(message);
    }
}
