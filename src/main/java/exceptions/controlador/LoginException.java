package exceptions.controlador;

public class LoginException extends Exception{
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
