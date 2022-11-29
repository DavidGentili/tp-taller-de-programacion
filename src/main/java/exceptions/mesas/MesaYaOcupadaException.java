package exceptions.mesas;

/**
 * Excepcion emitida cuando la mesa ya se encuentra ocupada
 */
public class MesaYaOcupadaException extends Exception{
    public MesaYaOcupadaException() {
    }

    public MesaYaOcupadaException(String message) {
        super(message);
    }
}
