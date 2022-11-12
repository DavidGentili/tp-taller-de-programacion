package exceptions;

public class ProductoYaExistenteException extends Exception {
    public ProductoYaExistenteException() {
    }

    public ProductoYaExistenteException(String message) {
        super(message);
    }
}
