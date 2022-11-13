package exceptions.operarios;

public class OperarioYaExistenteException extends Exception{
    public OperarioYaExistenteException() {
    }

    public OperarioYaExistenteException(String message) {
        super(message);
    }
}
