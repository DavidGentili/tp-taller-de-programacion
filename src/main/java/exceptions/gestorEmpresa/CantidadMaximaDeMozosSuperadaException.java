package exceptions.gestorEmpresa;

public class CantidadMaximaDeMozosSuperadaException extends Exception{
    public CantidadMaximaDeMozosSuperadaException() {
    }

    public CantidadMaximaDeMozosSuperadaException(String message) {
        super(message);
    }
}
