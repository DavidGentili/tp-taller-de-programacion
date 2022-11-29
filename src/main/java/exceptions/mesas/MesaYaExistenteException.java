package exceptions.mesas;

/**
 * Excepcion emitida cuando la mesa ya existe
 */
public class MesaYaExistenteException extends Exception{
    public MesaYaExistenteException() {
    }

    public MesaYaExistenteException(String message) {
        super(message);
    }
}
