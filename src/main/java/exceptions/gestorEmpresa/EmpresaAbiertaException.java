package exceptions.gestorEmpresa;

public class EmpresaAbiertaException extends Exception{
    public EmpresaAbiertaException() {
    }

    public EmpresaAbiertaException(String message) {
        super(message);
    }
}
