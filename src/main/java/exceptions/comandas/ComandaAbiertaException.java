package exceptions.comandas;

/**
 * Excepcion emitida cuando hay una comanda abierta
 */
public class ComandaAbiertaException extends Exception{
    public ComandaAbiertaException() {
    }

    public ComandaAbiertaException(String message) {
        super(message);
    }
}