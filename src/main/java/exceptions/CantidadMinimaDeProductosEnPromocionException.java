package exceptions;

public class CantidadMinimaDeProductosEnPromocionException extends Exception{
    public CantidadMinimaDeProductosEnPromocionException() {
    }

    public CantidadMinimaDeProductosEnPromocionException(String message) {
        super(message);
    }
}
