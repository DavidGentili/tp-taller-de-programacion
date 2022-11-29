package exceptions.operarios;

/**
 * Excepcion emitida cuando no se encuentra el operario
 */
public class OperarioNoEncontradoException extends Exception {
    public OperarioNoEncontradoException() {
    }

    public OperarioNoEncontradoException(String message) {
        super(message);
    }
}
