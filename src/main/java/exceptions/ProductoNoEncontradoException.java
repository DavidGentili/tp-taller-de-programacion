package exceptions;

public class ProductoNoEncontradoException extends Exception{
    public ProductoNoEncontradoException() {
    }

    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
