package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo eliminar un producto
 */
public class ErrorAlEliminarProductoException extends Exception{
    public ErrorAlEliminarProductoException() {
    }

    public ErrorAlEliminarProductoException(String message) {
        super(message);
    }
}
