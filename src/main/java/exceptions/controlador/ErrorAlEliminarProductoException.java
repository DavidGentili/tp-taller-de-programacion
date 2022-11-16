package exceptions.controlador;

public class ErrorAlEliminarProductoException extends Exception{
    public ErrorAlEliminarProductoException() {
    }

    public ErrorAlEliminarProductoException(String message) {
        super(message);
    }
}
