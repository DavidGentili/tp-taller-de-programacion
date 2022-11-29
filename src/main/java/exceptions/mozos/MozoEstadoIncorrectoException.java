package exceptions.mozos;

/**
 * Excepcion emitida cuando el estado del mozo es incorrecto
 */
public class MozoEstadoIncorrectoException extends Exception{
    public MozoEstadoIncorrectoException() {
    }

    public MozoEstadoIncorrectoException(String message) {
        super(message);
    }
}
