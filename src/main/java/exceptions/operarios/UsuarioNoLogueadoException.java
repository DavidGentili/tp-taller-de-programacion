package exceptions.operarios;

/**
 * Excepcion emitida cuando el usuario no esta logueado
 */
public class UsuarioNoLogueadoException extends Exception{
    public UsuarioNoLogueadoException() {
    }

    public UsuarioNoLogueadoException(String message) {
        super(message);
    }
}
