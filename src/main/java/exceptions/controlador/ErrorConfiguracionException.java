package exceptions.controlador;

/**
 * Excepcion emitida cuando hubo un error de configuracion
 */
public class ErrorConfiguracionException extends Exception{
    public ErrorConfiguracionException() {
    }

    public ErrorConfiguracionException(String message) {
        super(message);
    }
}
