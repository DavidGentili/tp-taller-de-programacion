package exceptions.productos;

public class StockInsuficienteException extends Exception{
    public StockInsuficienteException() {
    }

    public StockInsuficienteException(String message) {
        super(message);
    }
}
