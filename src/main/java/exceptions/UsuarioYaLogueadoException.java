package exceptions;

public class UsuarioYaLogueadoException extends Exception{
    public UsuarioYaLogueadoException() {
    }

    public UsuarioYaLogueadoException(String message) {
        super(message);
    }
}
