package exceptions;

public class CantidadMinimaDeProductosException extends Exception{
    public CantidadMinimaDeProductosException() {
    }

    public CantidadMinimaDeProductosException(String message) {
        super(message);
    }
}
