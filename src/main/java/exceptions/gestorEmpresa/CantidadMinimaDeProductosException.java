package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se supero la cantidad minima de productos en el sistema
 */
public class CantidadMinimaDeProductosException extends Exception{
    public CantidadMinimaDeProductosException() {
    }

    public CantidadMinimaDeProductosException(String message) {
        super(message);
    }
}
