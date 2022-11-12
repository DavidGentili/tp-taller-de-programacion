package exceptions;

public class ContraseñaIncorrectaException extends Exception{
    public ContraseñaIncorrectaException(){
        super();
    }
    public ContraseñaIncorrectaException(String message){
        super(message);
    }
}
