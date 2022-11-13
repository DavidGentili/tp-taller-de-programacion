package exceptions.promociones;

public class PromocionYaExistenteException extends Exception{
    public PromocionYaExistenteException() {
    }

    public PromocionYaExistenteException(String message) {
        super(message);
    }
}
