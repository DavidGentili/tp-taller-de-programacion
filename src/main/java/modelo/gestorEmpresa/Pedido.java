package modelo.gestorEmpresa;

import modelo.configEmpresa.Producto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Pedido implements Serializable {
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

	/**
	 * Crea una instancia de pedido
	 * pre: producto != null
	 * 		cantidad > 0
	 * @param producto : Producto del pedido
	 * @param cantidad : cantidad solicitada
	 * @param fecha : la fecha del pedido
	 */
	public Pedido(Producto producto, int cantidad, GregorianCalendar fecha) {
		assert producto != null : "El producto no puede ser nulo";
		assert cantidad > 0 : "La catidad debe ser positiva";
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;
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
