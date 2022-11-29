package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no se puede realizar una accion porque la empresa se encuentra cerrada
 */
public class EmpresaCerradaException extends Exception{
    public EmpresaCerradaException() {
    }

    public EmpresaCerradaException(String message) {
        super(message);
    }
}
