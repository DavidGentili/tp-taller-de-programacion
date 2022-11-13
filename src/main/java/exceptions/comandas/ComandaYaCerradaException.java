package exceptions.comandas;

public class ComandaYaCerradaException extends Exception{
    public ComandaYaCerradaException(){
        super();
    }
    public ComandaYaCerradaException(String message){
        super(message);
    }
}
