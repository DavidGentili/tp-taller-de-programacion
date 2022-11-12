package exceptions;

public class OperarioYaExistenteException extends Exception{
    public OperarioYaExistenteException() {
    }

    public OperarioYaExistenteException(String message) {
        super(message);
    }
}
