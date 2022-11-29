package exceptions.mozos;

/**
 * Excepcion emitida cuando no se encuentra el mozo
 */
public class MozoNoEncontradoException extends Exception{
    public MozoNoEncontradoException() {
    }

    public MozoNoEncontradoException(String message) {
        super(message);
    }
}

