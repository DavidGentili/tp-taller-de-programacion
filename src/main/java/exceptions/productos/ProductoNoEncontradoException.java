package exceptions.productos;

/**
 * Excepcion emitida cuando no se encontro el producto buscado
 */
public class ProductoNoEncontradoException extends Exception{
    public ProductoNoEncontradoException() {
    }

    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
