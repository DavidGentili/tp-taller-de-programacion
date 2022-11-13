package exceptions.productos;

public class ProductoNoEncontradoException extends Exception{
    public ProductoNoEncontradoException() {
    }

    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
