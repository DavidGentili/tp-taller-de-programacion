package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo eliminar una mesa
 */
public class ErrorAlEliminarMesaException extends Exception{
    public ErrorAlEliminarMesaException() {
    }

    public ErrorAlEliminarMesaException(String message) {
        super(message);
    }
}
