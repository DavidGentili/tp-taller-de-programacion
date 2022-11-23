package exceptions.operarios;

/**
 * Si no se cambio la contraseña del usuario
 */
public class NoSeCambioContraseniaException extends Exception{

    public NoSeCambioContraseniaException() {
    }

    public NoSeCambioContraseniaException(String message) {
        super(message);
    }
}
