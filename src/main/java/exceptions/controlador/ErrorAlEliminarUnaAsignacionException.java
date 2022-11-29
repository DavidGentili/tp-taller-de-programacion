package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo eliminar una asignacion mozo mesa
 */
public class ErrorAlEliminarUnaAsignacionException extends Exception{
    public ErrorAlEliminarUnaAsignacionException() {
    }

    public ErrorAlEliminarUnaAsignacionException(String message) {
        super(message);
    }
}
