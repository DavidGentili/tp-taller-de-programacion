package modelo.gestorEmpresa;

import modelo.configEmpresa.Producto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Pedido implements Serializable, Cloneable {
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

		invariante();
		assert this.producto == producto : "El producto no se asigno correctamente";
		assert this.cantidad == cantidad : "No se asigno correctamente la cantidad";
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
		assert fecha != null :" La fecha no puede ser nula ";

		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;

		invariante();
		assert this.producto == producto : "El producto no se asigno correctamente";
		assert this.cantidad == cantidad : "No se asigno correctamente la cantidad";
		assert this.fecha == fecha : "No se asigno correctamente la fecha";
	}

	/**
	 * Retorna el producto del pedido
	 * @return producto del pedido
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Retorna la cantidad del pedido
	 * @return cantidad del pedido
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * retorna la fecha del pedido
	 * @return fecha del pedido
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * Retorna un clon del pedido
	 * @return clon del pedido
	 * @throws CloneNotSupportedException si hay un error al clonar el pedido
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Pedido clon = (Pedido) super.clone();
		clon.producto = (Producto) producto.clone();
		return clon;
	}

	private void invariante(){
		assert fecha != null : "La fecha no puede ser nula";
		assert producto != null : "El producton no puede ser nulo";
		assert cantidad > 0 : "La cantidad debe ser positiva";
	}
}
