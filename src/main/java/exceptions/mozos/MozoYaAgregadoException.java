package exceptions.mozos;

/**
 * Excepcion emitida cuando el mozo ya esta agregado
 */
public class MozoYaAgregadoException extends Exception{
    public MozoYaAgregadoException() {
    }

    public MozoYaAgregadoException(String message) {
        super(message);
    }
}
