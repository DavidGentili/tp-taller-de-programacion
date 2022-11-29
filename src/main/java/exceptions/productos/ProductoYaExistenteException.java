package exceptions.productos;

/**
 * Excepcion emitida cuando el producto ya se encuentra en la coleccion
 */
public class ProductoYaExistenteException extends Exception {
    public ProductoYaExistenteException() {
    }

    public ProductoYaExistenteException(String message) {
        super(message);
    }
}
