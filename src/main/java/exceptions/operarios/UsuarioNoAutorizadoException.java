package exceptions.operarios;

public class UsuarioNoAutorizadoException extends Exception{
    public UsuarioNoAutorizadoException() {
    }

    public UsuarioNoAutorizadoException(String message) {
        super(message);
    }
}
