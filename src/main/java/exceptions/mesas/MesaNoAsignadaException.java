package exceptions.mesas;

/**
 * Excepcion emitida cuando la mesa no esta asignada
 */
public class MesaNoAsignadaException extends Exception{
    public MesaNoAsignadaException() {
    }

    public MesaNoAsignadaException(String message) {
        super(message);
    }
}
