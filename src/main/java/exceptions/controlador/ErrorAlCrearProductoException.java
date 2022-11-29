package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo crear un producto
 */
public class ErrorAlCrearProductoException extends Exception{

    public ErrorAlCrearProductoException() {
    }

    public ErrorAlCrearProductoException(String message) {
        super(message);
    }
}
