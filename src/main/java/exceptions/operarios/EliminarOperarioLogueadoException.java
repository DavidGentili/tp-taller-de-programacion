package exceptions.operarios;

/**
 * Excepcion emitida cuando no se intenta eliminar un usuario logueado
 */
public class EliminarOperarioLogueadoException extends Exception{
    public EliminarOperarioLogueadoException() {
    }

    public EliminarOperarioLogueadoException(String message) {
        super(message);
    }
}
