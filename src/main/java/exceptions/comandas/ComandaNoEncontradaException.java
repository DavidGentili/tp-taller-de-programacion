package exceptions.comandas;

/**
 * Excepcion emitida cuando  no se encontro una comanda
 */
public class ComandaNoEncontradaException extends Exception{
    public ComandaNoEncontradaException() {
    }

    public ComandaNoEncontradaException(String message) {
        super(message);
    }
}
