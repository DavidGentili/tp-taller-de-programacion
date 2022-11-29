package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo eliminar un mozo
 */
public class ErrorAlEliminarMozoException extends Exception{
    public ErrorAlEliminarMozoException() {
    }

    public ErrorAlEliminarMozoException(String message) {
        super(message);
    }
}
