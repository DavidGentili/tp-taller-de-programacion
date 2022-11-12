package exceptions;

public class MesaYaExistenteException extends Exception{
    public MesaYaExistenteException() {
    }

    public MesaYaExistenteException(String message) {
        super(message);
    }
}
