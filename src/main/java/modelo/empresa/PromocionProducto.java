package modelo.empresa;

import java.util.Collection;

import modelo.configEmpresa.Producto;

public class PromocionProducto extends Promocion {

	

	private Collection<Producto> productos;
	private boolean dosporuno;
	private boolean dtoporcant;
	private int cantminima;
	private double preciounitario;
	
	public PromocionProducto(int idprom, String dias) {
		super(idprom, dias);
		// TODO Auto-generated constructor stub
	
	}
	/**
     * Se encarga de agregar un producto a la promocion
     * @param producto : producto a agregar
     * pre : producto != null
     * post : se agrega un nuevo producto a la coleccion
     */
	
	public void AgregaPorducto(Producto producto) {
		
	}

	public Collection<Producto> getProductos() {
		return productos;
	}

	public boolean isDosporuno() {
		return dosporuno;
	}

	public boolean isDtoporcant() {
		return dtoporcant;
	}

	public int getCantminima() {
		return cantminima;
	}

	public double getPreciounitario() {
		return preciounitario;
	}
	
	
	
	
	
	
}
