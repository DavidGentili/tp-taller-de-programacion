package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se puede realizar una accion porque hay mozo sin estado asignado
 */
public class HayMozoSinEstadoAsignadoException extends Exception{
    public HayMozoSinEstadoAsignadoException() {
    }

    public HayMozoSinEstadoAsignadoException(String message) {
        super(message);
    }
}
