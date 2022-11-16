package exceptions.controlador;

public class ErrorComandaException extends Exception{
    public ErrorComandaException() {
    }

    public ErrorComandaException(String message) {
        super(message);
    }
}
