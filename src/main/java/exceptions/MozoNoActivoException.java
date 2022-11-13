package exceptions;

public class MozoNoActivoException extends Exception{
    public MozoNoActivoException() {
    }

    public MozoNoActivoException(String message) {
        super(message);
    }
}
