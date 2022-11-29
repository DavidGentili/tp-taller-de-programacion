package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando se supero la cantidad de mozos activos
 */
public class CantidadMaximaDeMozosActivosException extends Exception{
    public CantidadMaximaDeMozosActivosException() {
    }

    public CantidadMaximaDeMozosActivosException(String message) {
        super(message);
    }
}
