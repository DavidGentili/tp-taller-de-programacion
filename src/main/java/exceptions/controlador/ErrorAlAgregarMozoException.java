package exceptions.controlador;

/**
 * Excepcion emitida cuando no se puedo agregar un mozo
 */
public class ErrorAlAgregarMozoException extends Exception{
    public ErrorAlAgregarMozoException() {
    }

    public ErrorAlAgregarMozoException(String message) {
        super(message);
    }
}
