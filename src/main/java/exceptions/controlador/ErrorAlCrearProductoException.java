package exceptions.controlador;

public class ErrorAlCrearProductoException extends Exception{

    public ErrorAlCrearProductoException() {
    }

    public ErrorAlCrearProductoException(String message) {
        super(message);
    }
}
