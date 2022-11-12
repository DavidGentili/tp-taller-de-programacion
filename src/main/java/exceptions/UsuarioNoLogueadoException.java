package exceptions;

public class UsuarioNoLogueadoException extends Exception{
    public UsuarioNoLogueadoException() {
    }

    public UsuarioNoLogueadoException(String message) {
        super(message);
    }
}
