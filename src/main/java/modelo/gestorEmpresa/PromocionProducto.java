package modelo.gestorEmpresa;

import enums.FormasDePago;
import helpers.FacturaHelpers;
import modelo.configEmpresa.Producto;

import java.io.Serializable;
import java.util.ArrayList;

public class PromocionProducto extends Promocion implements Serializable {
	private Producto producto;
	private boolean dosPorUno;
	private boolean dtoPorCant;
	private int cantMinima;
	private double precioUnitario;

	public PromocionProducto(String dias, Producto producto, boolean dosPorUno, boolean dtoPorCant, int cantMin, double precioUnitario){
		super(dias);
		assert dosPorUno || dtoPorCant : "Ambos no pueden ser falsos";
		assert producto != null : "El producto no puede ser nulo";
		assert cantMin >= 0 : "La cantidad minima debe ser mayor a 0";
		assert precioUnitario >= 0 : "El precio unitario debe ser mayor a 0";
		this.producto = producto;
		this.dosPorUno = dosPorUno;
		this.dtoPorCant = dtoPorCant;
		this.cantMinima = cantMin;
		this.precioUnitario = precioUnitario;
	}

	public PromocionProducto(String dias, Producto producto, int cantMin, double precioUnitario) {
		super(dias);
		assert producto != null : "El producto no puede ser nulo";
		assert cantMin > 0 : "La cantidad minima debe ser mayor a 0";
		assert precioUnitario > 0 : "El precio unitario debe ser mayor a 0";
		this.producto = producto;
		this.dtoPorCant = true;
		this.cantMinima = cantMin;
		this.precioUnitario = precioUnitario;
	}

	public PromocionProducto(String dias, Producto producto){
		super(dias);
		assert producto != null : "El producto no puede ser nulo";
		this.producto = producto;
		this.dosPorUno = true;
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

	public Producto getProducto(){ return producto;}

	
	private void invariante(){
		assert this.dosPorUno | this.dtoPorCant : "Dos por uno y dto por cantidad no pueden ser nulos en simultaneo";
	}

	public boolean isAcumulable(){
		return true;
	}


	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago) {
		if(!activa || !FacturaHelpers.correspondeDia(dias))
			return false;
		boolean aplica = false;
		int i = 0;
		while (i < pedidos.size() && !aplica){
			if(pedidos.get(i).getProducto().getId() == producto.getId())
				aplica = true;
			i++;
		}
		return aplica;
	}

	public double getDescuento(ArrayList<Pedido> pedidos){
		if(dosPorUno)
			return calculaDescuentoDosPorUno(pedidos);
		else
			return calculoDescuentoPorCantidad(pedidos);
	}

	private double calculaDescuentoDosPorUno(ArrayList<Pedido> pedidos){
		double desc = 0;
		for (Pedido pedido : pedidos){
			if(pedido.getProducto().getId() == producto.getId())
				desc += Math.floor(pedido.getCantidad()/2) * pedido.getProducto().getPrecioVenta();
		}
		return desc;
	}

	private double calculoDescuentoPorCantidad(ArrayList<Pedido> pedidos){
		double desc = 0;
		for (Pedido pedido : pedidos){
			if(pedido.getProducto().getId() == producto.getId() && pedido.getCantidad() >= cantMinima)
				desc += (producto.getPrecioVenta() - precioUnitario) * pedido.getCantidad();
		}
		return desc;
	}

	@Override
	public String toString() {
		return String.format("| %-12s %-5s  %s |", producto.getNombre(), dosPorUno ? "2x1" : "xCant", dias);
	}
}
