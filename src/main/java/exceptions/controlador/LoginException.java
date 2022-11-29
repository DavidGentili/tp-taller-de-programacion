package exceptions.controlador;

/**
 * Excepcion emitida cuando hubo un intento de login incorrecto
 */
public class LoginException extends Exception{
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
