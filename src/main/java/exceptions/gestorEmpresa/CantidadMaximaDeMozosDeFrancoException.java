package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando se supero la cantidad de mozo de franco
 */
public class CantidadMaximaDeMozosDeFrancoException extends Exception{
    public CantidadMaximaDeMozosDeFrancoException() {
    }

    public CantidadMaximaDeMozosDeFrancoException(String message) {
        super(message);
    }
}
