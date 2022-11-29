package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se puede realizar una accion debido a que la empresa se encuentra abierta
 */
public class EmpresaAbiertaException extends Exception{
    public EmpresaAbiertaException() {
    }

    public EmpresaAbiertaException(String message) {
        super(message);
    }
}
