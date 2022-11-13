package exceptions;

public class NoHayMozosAsignadosException extends Exception{
    public NoHayMozosAsignadosException() {
    }

    public NoHayMozosAsignadosException(String message) {
        super(message);
    }
}
