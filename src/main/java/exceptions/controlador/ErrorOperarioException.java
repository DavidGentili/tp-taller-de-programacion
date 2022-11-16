package exceptions.controlador;

public class ErrorOperarioException extends Exception{
    public ErrorOperarioException() {
    }

    public ErrorOperarioException(String message) {
        super(message);
    }
}
