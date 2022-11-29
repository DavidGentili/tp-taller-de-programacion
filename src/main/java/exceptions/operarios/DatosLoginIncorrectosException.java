package exceptions.operarios;

/**
 * Excepcion emitida cuando los datos de login son incorrectos
 */
public class DatosLoginIncorrectosException extends Exception{
    public DatosLoginIncorrectosException(){
        super();
    }
    public DatosLoginIncorrectosException(String message){
        super(message);
    }
}
