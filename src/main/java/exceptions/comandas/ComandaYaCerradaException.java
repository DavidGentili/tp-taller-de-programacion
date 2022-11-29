package exceptions.comandas;

/**
 * Excepcion emitida cuando la comanda ya se encuentra cerrada
 */
public class ComandaYaCerradaException extends Exception{
    public ComandaYaCerradaException(){
        super();
    }
    public ComandaYaCerradaException(String message){
        super(message);
    }
}
