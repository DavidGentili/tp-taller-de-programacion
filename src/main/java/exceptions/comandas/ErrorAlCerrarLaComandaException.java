package exceptions.comandas;

public class ErrorAlCerrarLaComandaException extends Exception{
    public ErrorAlCerrarLaComandaException() {
    }

    public ErrorAlCerrarLaComandaException(String message) {
        super(message);
    }
}
