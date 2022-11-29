package exceptions;

/**
 * Cuando el numero de id es incorrecto
 */
public class IdIncorrectoException extends Exception{
    public IdIncorrectoException() {
    }

    public IdIncorrectoException(String message) {
        super(message);
    }
}
