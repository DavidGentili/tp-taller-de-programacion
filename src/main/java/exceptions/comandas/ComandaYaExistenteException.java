package exceptions.comandas;

public class ComandaYaExistenteException extends Exception{
    public ComandaYaExistenteException() {
    }

    public ComandaYaExistenteException(String message) {
        super(message);
    }
}
