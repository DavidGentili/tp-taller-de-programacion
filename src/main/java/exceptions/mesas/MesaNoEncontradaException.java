package exceptions.mesas;

/**
 * Excepcion emitida cuando la mesa no es encontrada
 */
public class MesaNoEncontradaException extends Exception{
    public MesaNoEncontradaException() {
    }

    public MesaNoEncontradaException(String message) {
        super(message);
    }
}
