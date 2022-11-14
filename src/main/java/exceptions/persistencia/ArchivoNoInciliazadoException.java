package exceptions.persistencia;

public class ArchivoNoInciliazadoException extends Exception{

    public ArchivoNoInciliazadoException(){
        super();
    }
    public ArchivoNoInciliazadoException(String message){
        super(message);
    }
}
