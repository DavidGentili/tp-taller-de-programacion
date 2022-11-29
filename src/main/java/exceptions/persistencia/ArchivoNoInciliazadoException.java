package exceptions.persistencia;

/**
 * Excepcion emitida cuando no se inicializo correctamente la persistencia
 */
public class ArchivoNoInciliazadoException extends Exception{

    public ArchivoNoInciliazadoException(){
        super();
    }
    public ArchivoNoInciliazadoException(String message){
        super(message);
    }
}
