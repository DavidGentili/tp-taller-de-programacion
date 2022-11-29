package exceptions.operarios;
/**
 * Excepcion emitida cuando no se cambio la contraseña del usuario
 */
public class NoSeCambioContraseniaException extends Exception{

    public NoSeCambioContraseniaException() {
    }

    public NoSeCambioContraseniaException(String message) {
        super(message);
    }
}
