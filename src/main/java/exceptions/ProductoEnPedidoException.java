package exceptions;

public class ProductoEnPedidoException extends Exception{
    public ProductoEnPedidoException() {
    }

    public ProductoEnPedidoException(String message) {
        super(message);
    }
}
