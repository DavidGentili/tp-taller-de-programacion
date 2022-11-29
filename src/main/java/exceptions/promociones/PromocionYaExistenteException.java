package exceptions.promociones;

/**
 * Excepcion emitida cuando la promocion ya se encuentra en la coleccion
 */
public class PromocionYaExistenteException extends Exception{
    public PromocionYaExistenteException() {
    }

    public PromocionYaExistenteException(String message) {
        super(message);
    }
}
