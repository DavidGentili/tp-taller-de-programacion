package exceptions;

public class PromocionYaExistenteException extends Exception{
    public PromocionYaExistenteException() {
    }

    public PromocionYaExistenteException(String message) {
        super(message);
    }
}
