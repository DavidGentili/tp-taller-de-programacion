package exceptions.operarios;

/**
 * Excepcion emitida cuando el operario se encuentra inactivo
 */
public class OperarioInactivoException extends Exception{
    public OperarioInactivoException() {
    }

    public OperarioInactivoException(String message) {
        super(message);
    }
}
