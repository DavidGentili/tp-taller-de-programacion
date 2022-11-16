package exceptions.controlador;

public class ErrorAlAgregarMesaException extends Exception{
    public ErrorAlAgregarMesaException() {
    }

    public ErrorAlAgregarMesaException(String message) {
        super(message);
    }
}
