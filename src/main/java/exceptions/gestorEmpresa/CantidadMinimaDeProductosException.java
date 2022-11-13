package exceptions.gestorEmpresa;

public class CantidadMinimaDeProductosException extends Exception{
    public CantidadMinimaDeProductosException() {
    }

    public CantidadMinimaDeProductosException(String message) {
        super(message);
    }
}
