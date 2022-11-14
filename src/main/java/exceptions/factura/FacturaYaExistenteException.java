package exceptions.factura;

public class FacturaYaExistenteException extends Exception{
    public FacturaYaExistenteException() {
    }

    public FacturaYaExistenteException(String message) {
        super(message);
    }
}
