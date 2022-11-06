package modelo.empresa;

import modelo.configEmpresa.Producto;

import java.util.Date;

public class Pedido {
    private Producto producto;
    private int cantidad;
    private Date fecha;
    
    
	public Pedido(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		//fecha actual
	}

    
}
