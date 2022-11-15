package exceptions.operarios;

public class ContraseniaIncorrectaException extends Exception{
    public ContraseniaIncorrectaException(){
        super();
    }
    public ContraseniaIncorrectaException(String message){
        super(message);
    }
}
