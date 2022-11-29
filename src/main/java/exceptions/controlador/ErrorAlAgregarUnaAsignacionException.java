package exceptions.controlador;

/**
 * Excepcion emitida cuando agregar una asignacion mozo mesa
 */
public class ErrorAlAgregarUnaAsignacionException extends Exception{
    public ErrorAlAgregarUnaAsignacionException() {
    }

    public ErrorAlAgregarUnaAsignacionException(String message) {
        super(message);
    }
}
