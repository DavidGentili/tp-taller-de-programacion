package exceptions.productos;

/**
 * Excepcion emitida cuando el producto se encuentra en el pedido
 */
public class ProductoEnPedidoException extends Exception{
    public ProductoEnPedidoException() {
    }

    public ProductoEnPedidoException(String message) {
        super(message);
    }
}
