package exceptions.operarios;

/**
 * Excepcion emitida cuando ya existe un usuario logueado
 */
public class UsuarioYaLogueadoException extends Exception{
    public UsuarioYaLogueadoException() {
    }

    public UsuarioYaLogueadoException(String message) {
        super(message);
    }
}
