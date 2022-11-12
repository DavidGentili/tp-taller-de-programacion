package exceptions;

public class OperarioInactivoException extends Exception{
    public OperarioInactivoException() {
    }

    public OperarioInactivoException(String message) {
        super(message);
    }
}
