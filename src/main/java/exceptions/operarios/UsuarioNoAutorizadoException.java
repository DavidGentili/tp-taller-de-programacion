package exceptions.operarios;

/**
 * Excepcion emitida cuando el usuarion o esta autorizado
 */
public class UsuarioNoAutorizadoException extends Exception{
    public UsuarioNoAutorizadoException() {
    }

    public UsuarioNoAutorizadoException(String message) {
        super(message);
    }
}
