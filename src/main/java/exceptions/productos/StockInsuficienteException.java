package exceptions.productos;

/**
 * Excepcion emitida cuando el stock es insuficiente
 */
public class StockInsuficienteException extends Exception{
    public StockInsuficienteException() {
    }

    public StockInsuficienteException(String message) {
        super(message);
    }
}
