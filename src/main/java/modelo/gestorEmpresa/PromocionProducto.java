package modelo.gestorEmpresa;

import modelo.configEmpresa.Producto;

import java.io.Serializable;

public class PromocionProducto extends Promocion implements Serializable {
	private Producto producto;
	private boolean dosPorUno;
	private boolean dtoPorCant;
	private int cantMinima;
	private double precioUnitario;
	
	public PromocionProducto(String dias, Producto producto, boolean dosPorUno, boolean dtoPorCant, int cantMin, double precioUnitario) {
		super(dias);
		assert (dosPorUno | dtoPorCant) : "No pueden ser ambos nulos";
		assert producto != null : "El producto no puede ser nulo";
		assert cantMin > 0 : "La cantidad minima debe ser mayor a 0";
		assert precioUnitario > 0 : "El precio unitario debe ser mayor a 0";
		this.producto = producto;
		this.dosPorUno = dosPorUno;
		this.dtoPorCant = dtoPorCant;
		this.cantMinima = cantMin;
		this.precioUnitario = precioUnitario;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
		invariante();
	}

	public void setDosPorUno(boolean dosPorUno) {
		this.dosPorUno = dosPorUno;
		invariante();
	}

	public void setDtoPorCant(boolean dtoPorCant) {
		this.dtoPorCant = dtoPorCant;
		invariante();
	}

	public void setCantMinima(int cantMinima) {
		this.cantMinima = cantMinima;
		invariante();
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
		invariante();
	}

	public boolean isDosPorUno() {
		return dosPorUno;
	}

	public boolean isDtoPorCant() {
		return dtoPorCant;
	}

	public int getCantMinima() {
		return cantMinima;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}
	
	private void invariante(){
		assert this.dosPorUno | this.dtoPorCant : "Dos por uno y dto por cantidad no pueden ser nulos en simultaneo";
	}
	
	
	
	
}
