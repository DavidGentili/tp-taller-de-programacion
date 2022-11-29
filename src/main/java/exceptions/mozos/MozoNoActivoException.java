package exceptions.mozos;

/**
 * Excepcion emitida cuando el mozo no se encuentra activo
 */
public class MozoNoActivoException extends Exception{
    public MozoNoActivoException() {
    }

    public MozoNoActivoException(String message) {
        super(message);
    }
}
