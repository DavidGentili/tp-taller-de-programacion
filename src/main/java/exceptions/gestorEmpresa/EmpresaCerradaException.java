package exceptions.gestorEmpresa;

public class EmpresaCerradaException extends Exception{
    public EmpresaCerradaException() {
    }

    public EmpresaCerradaException(String message) {
        super(message);
    }
}
