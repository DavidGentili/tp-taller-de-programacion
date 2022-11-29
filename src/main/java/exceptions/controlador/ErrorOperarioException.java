package exceptions.controlador;

/**
 * Excepcion emitida cuando hubo un error con un operario
 */
public class ErrorOperarioException extends Exception{
    public ErrorOperarioException() {
    }

    public ErrorOperarioException(String message) {
        super(message);
    }
}
