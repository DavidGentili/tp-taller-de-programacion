package exceptions;

public class DatosLoginIncorrectosException extends Exception{
    public DatosLoginIncorrectosException(){
        super();
    }
    public DatosLoginIncorrectosException(String message){
        super(message);
    }
}
