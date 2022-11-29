package exceptions.comandas;

/**
 * Excepcion emitida cuando no se pudo cerrar la comanda
 */
public class ErrorAlCerrarLaComandaException extends Exception{
    public ErrorAlCerrarLaComandaException() {
    }

    public ErrorAlCerrarLaComandaException(String message) {
        super(message);
    }
}
