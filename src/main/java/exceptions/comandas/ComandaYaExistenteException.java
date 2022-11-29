package exceptions.comandas;

/**
 * Excepcion emitida cuando ya existe dicha comanda en una coleccion
 */
public class ComandaYaExistenteException extends Exception{
    public ComandaYaExistenteException() {
    }

    public ComandaYaExistenteException(String message) {
        super(message);
    }
}
