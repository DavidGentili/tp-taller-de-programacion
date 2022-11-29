package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo agregar una mesa
 */
public class ErrorAlAgregarMesaException extends Exception{
    public ErrorAlAgregarMesaException() {
    }

    public ErrorAlAgregarMesaException(String message) {
        super(message);
    }
}
