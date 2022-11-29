package exceptions.factura;

/**
 * Excepcion emitida cuando la factura que se quiere agregar ya existe
 */
public class FacturaYaExistenteException extends Exception{
    public FacturaYaExistenteException() {
    }

    public FacturaYaExistenteException(String message) {
        super(message);
    }
}
