package exceptions.mesas;

/**
 * Excepcion emitida cuando la mesa ya fue liberada
 */
public class MesaYaLiberadaException extends Exception{
    public MesaYaLiberadaException() {
    }

    public MesaYaLiberadaException(String message) {
        super(message);
    }
}
