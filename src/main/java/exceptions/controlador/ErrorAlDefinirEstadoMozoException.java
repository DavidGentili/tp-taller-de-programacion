package exceptions.controlador;

/**
 * Excepcion emitida cuando no se pudo definir el estado de un mozo
 */
public class ErrorAlDefinirEstadoMozoException extends Exception{
    public ErrorAlDefinirEstadoMozoException() {
    }

    public ErrorAlDefinirEstadoMozoException(String message) {
        super(message);
    }
}
