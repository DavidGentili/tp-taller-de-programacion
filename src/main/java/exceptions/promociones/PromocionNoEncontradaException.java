package exceptions.promociones;

/**
 * Excepcion emitida cuando no se encontro la promocion
 */
public class PromocionNoEncontradaException extends Exception{
    public PromocionNoEncontradaException() {
    }

    public PromocionNoEncontradaException(String message) {
        super(message);
    }
}
