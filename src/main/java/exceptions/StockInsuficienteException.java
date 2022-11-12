package exceptions;

public class StockInsuficienteException extends Exception{
    public StockInsuficienteException() {
    }

    public StockInsuficienteException(String message) {
        super(message);
    }
}
