package exceptions.mesas;

public class MesaNoAsignadaException extends Exception{
    public MesaNoAsignadaException() {
    }

    public MesaNoAsignadaException(String message) {
        super(message);
    }
}
