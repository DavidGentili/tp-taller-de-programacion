package exceptions.mesas;

public class MesaYaOcupadaException extends Exception{
    public MesaYaOcupadaException() {
    }

    public MesaYaOcupadaException(String message) {
        super(message);
    }
}
