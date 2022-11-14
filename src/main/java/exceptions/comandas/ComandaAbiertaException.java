package exceptions.comandas;

public class ComandaAbiertaException extends Exception{
    public ComandaAbiertaException() {
    }

    public ComandaAbiertaException(String message) {
        super(message);
    }
}
