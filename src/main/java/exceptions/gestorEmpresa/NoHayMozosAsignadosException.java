package exceptions.gestorEmpresa;

/**
 * Excepcion emitida cuando no hay mozo asignados a las mesas
 */
public class NoHayMozosAsignadosException extends Exception{
    public NoHayMozosAsignadosException() {
    }

    public NoHayMozosAsignadosException(String message) {
        super(message);
    }
}
