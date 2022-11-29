package exceptions.operarios;

/**
 * Excepcion emitida cuando el operario ya existe
 */
public class OperarioYaExistenteException extends Exception{
    public OperarioYaExistenteException() {
    }

    public OperarioYaExistenteException(String message) {
        super(message);
    }
}
