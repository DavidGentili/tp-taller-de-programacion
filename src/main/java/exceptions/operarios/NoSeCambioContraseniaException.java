package exceptions.operarios;

public class NoSeCambioContraseniaException extends Exception{

    public NoSeCambioContraseniaException() {
    }

    public NoSeCambioContraseniaException(String message) {
        super(message);
    }
}
