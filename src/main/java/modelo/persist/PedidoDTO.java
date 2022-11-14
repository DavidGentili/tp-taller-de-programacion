package modelo.persist;

import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Producto;
import modelo.gestorEmpresa.Pedido;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class PedidoDTO implements Serializable {
    private int idProducto;
    private int cantidad;
    private GregorianCalendar fecha;

    public PedidoDTO(Pedido pedido){
        this.cantidad = pedido.getCantidad();
        this.idProducto = pedido.getProducto().getId();
        this.fecha = pedido.getFecha();
    }

    public Pedido getPedido(){
        Producto producto = ConfiguracionEmpresa.getInstance().getProductoById(idProducto);
        return new Pedido(producto, cantidad, fecha);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }
}
