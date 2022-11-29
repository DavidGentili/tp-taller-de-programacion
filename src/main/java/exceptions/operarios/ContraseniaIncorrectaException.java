package exceptions.operarios;

/**
 * Excepcion emitida cuando la contraseña es incorrecta
 */
public class ContraseniaIncorrectaException extends Exception{
    public ContraseniaIncorrectaException(){
        super();
    }
    public ContraseniaIncorrectaException(String message){
        super(message);
    }
}
