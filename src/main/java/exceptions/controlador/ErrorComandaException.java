package exceptions.controlador;

/**
 * Excepcion emitida cuando hubo un error con la comanda
 */
public class ErrorComandaException extends Exception{
    public ErrorComandaException() {
    }

    public ErrorComandaException(String message) {
        super(message);
    }
}
