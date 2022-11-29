package exceptions.comandas;

/**
 * Excepcion emitida cuando no se pudo crear la comanda
 */
public class ErrorAlCrearComandaException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ErrorAlCrearComandaException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ErrorAlCrearComandaException(String message) {
        super(message);
    }
}
