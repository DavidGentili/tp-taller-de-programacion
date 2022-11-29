package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando se supero la cantidad de mozos
 */
public class CantidadMaximaDeMozosSuperadaException extends Exception{
    public CantidadMaximaDeMozosSuperadaException() {
    }

    public CantidadMaximaDeMozosSuperadaException(String message) {
        super(message);
    }
}
