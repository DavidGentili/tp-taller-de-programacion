package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se cumplio con la cantidad minima de productos en promocion
 */
public class CantidadMinimaDeProductosEnPromocionException extends Exception{
    public CantidadMinimaDeProductosEnPromocionException() {
    }

    public CantidadMinimaDeProductosEnPromocionException(String message) {
        super(message);
    }
}
