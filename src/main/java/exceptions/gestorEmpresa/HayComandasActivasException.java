package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se puede realizar una accion porque hay comandas abiertas
 */
public class HayComandasActivasException extends Exception{
    public HayComandasActivasException() {
    }

    public HayComandasActivasException(String message) {
        super(message);
    }
}
