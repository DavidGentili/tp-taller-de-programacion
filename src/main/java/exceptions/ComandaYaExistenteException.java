package exceptions;

public class ComandaYaExistenteException extends Exception{
    public ComandaYaExistenteException() {
    }

    public ComandaYaExistenteException(String message) {
        super(message);
    }
}
