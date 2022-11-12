package modelo.empresa;

import modelo.configEmpresa.Producto;

import java.util.Date;
import java.util.GregorianCalendar;

public class Pedido {
    private Producto producto;
    private int cantidad;
    private GregorianCalendar fecha;

	/**
	 * Crea una instancia de pedido
	 * pre: producto != null
	 * 		cantidad > 0
	 * @param producto : Producto del pedido
	 * @param cantidad : cantidad solicitada
	 */
	public Pedido(Producto producto, int cantidad) {
		assert producto != null : "El producto no puede ser nulo";
		assert cantidad > 0 : "La catidad debe ser positiva";
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = (GregorianCalendar) GregorianCalendar.getInstance();
	}


	public Producto getProducto() {
		return producto;
	}

	public int getCantidad() {
		return cantidad;
	}


	public GregorianCalendar getFecha() {
		return fecha;
	}

	
    
}
