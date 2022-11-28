package modelo.gestorEmpresa;

import enums.FormasDePago;
import helpers.FacturaHelpers;
import modelo.configEmpresa.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PromocionProducto extends Promocion implements Serializable {
	private Producto producto;
	private boolean dosPorUno;
	private boolean dtoPorCant;
	private int cantMinima;
	private double precioUnitario;

	/**
	 * Instancia una promocion de producto
	 * pre: dias != null && !dias.isEmpty && !dias.blank
	 * 		producto != null
	 * 		cantMin >= 0
	 * 		precion unitario >= 0
	 * @param dias dias de la promocion
	 * @param producto producto de la promocion
	 * @param dosPorUno si la promociones es dos por uno
	 * @param dtoPorCant si la promocion es descuento por cantidad
	 * @param cantMin cantidad minima
	 * @param precioUnitario precio unitario
	 */
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

	/**
	 * Instancia una promocion de producto
	 * pre: dias != null && !dias.isEmpty && !dias.blank
	 * 		producto != null
	 * 		cantMin >= 0
	 * 		precion unitario >= 0
	 * @param dias dias de la promocion
	 * @param producto producto de la promocion
	 * @param cantMin cantidad minima
	 * @param precioUnitario precio unitario
	 */
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

	/**
	 * Instancia una promocion de producto
	 * pre: dias != null && !dias.isEmpty && !dias.blank
	 * 		producto != null
	 * @param dias dias de la promocion
	 * @param producto producto de la promocion
	 */
	public PromocionProducto(String dias, Producto producto){
		super(dias);
		assert producto != null : "El producto no puede ser nulo";
		this.producto = producto;
		this.dosPorUno = true;
	}

	/**
	 * Determina el producto de la promocion
	 * @param producto productp de la promocion
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
		invariante();
	}

	/**
	 * Determina si la promocion es dos por uno
	 * @param dosPorUno si la promocion es dos por uno
	 */
	public void setDosPorUno(boolean dosPorUno) {
		this.dosPorUno = dosPorUno;
		invariante();
	}

	/**
	 * Determina si la promocion es de descuento por cantidad
	 * @param dtoPorCant si la promocion es de descuento por cantidad
	 */
	public void setDtoPorCant(boolean dtoPorCant) {
		this.dtoPorCant = dtoPorCant;
		invariante();
	}

	/**
	 * Determina la cantidad minima de productos
	 * pre : cantMinima >= 0
	 * @param cantMinima cantidad minima de productos
	 */
	public void setCantMinima(int cantMinima) {
		assert cantMinima >= 0 : "La cantidad minima debe ser mayor o igual a cero";
		this.cantMinima = cantMinima;

		invariante();
		assert this.cantMinima == cantMinima : "No se asigno correctamente la cantidad minima";
	}

	/**
	 * Determina el precio unitario
	 * pre : precioUnitario >= 0
	 * @param precioUnitario Precio unitario
	 */
	public void setPrecioUnitario(double precioUnitario) {
		assert precioUnitario >= 0 : "El precio unitario no puede ser negativo";
		this.precioUnitario = precioUnitario;
		invariante();
		assert this.precioUnitario == precioUnitario : "No se asigno correctamente el precio unitario";
	}

	/**
	 * Retorna si la promociones es de dos por uno
	 * @return si la promociones es de dos por uno
	 */
	public boolean isDosPorUno() {
		return dosPorUno;
	}

	/**
	 * Retorna si la promocion es de descuento por cantidad
	 * @return si la promocion es de descuento por cantidad
	 */
	public boolean isDtoPorCant() {
		return dtoPorCant;
	}

	/**
	 * Retorna la cantidad minima de la promocion
	 * @return cantidad minima de la promocion
	 */
	public int getCantMinima() {
		return cantMinima;
	}

	/**
	 * Retorna el precio unitario de la promocion
	 * @return precio unitario de la promocion
	 */
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * Retorna el producto de la promocion
	 * @return producto de la promocion
	 */
	public Producto getProducto(){ return producto;}

	/**
	 * Retorna si la promocion es acumulable
	 * @return si a promocion es acumulable
	 */
	public boolean isAcumulable(){
		return true;
	}


	/**
	 * Retorna si la promocion aplica con una lista de pedidos y una forma de pago
	 * pre: pedidos != null
	 * 		formaDePago != null
	 * @param pedidos lista de pedidos
	 * @param formaDePago forma de pago
	 * @return si la promocion aplica
	 */
	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago) {
		assert pedidos != null : "La lista de pedidos no puede ser nula";
		assert formaDePago != null : "La forma de pago no puede ser nula";
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

	/**
	 * Retorna si la promocion es aplicable
	 * pre: pedidos != null
	 * 		formaDePago != null
	 * 		fecha != null
	 * @param pedidos lista de pedidos
	 * @param formaDePago forma de pago
	 * @param fecha fecha de la compra
	 * @return si la promociones es aplicable
	 */
	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago, GregorianCalendar fecha){
		assert pedidos != null : "La lista de pedidos no puede ser nula";
		assert formaDePago != null : "La forma de pago no puede ser nula";
		assert fecha != null : "La fecha no puede ser nula";
		if(!activa || !FacturaHelpers.correspondeDia(dias, fecha))
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

	/**
	 * Obtiene el descuento correspondiente
	 * pre : pedidos != null
	 * @param pedidos lista de pedidos
	 * @return descuenco correspondiente
	 */
	public double getDescuento(ArrayList<Pedido> pedidos){
		assert pedidos != null : "Los pedidos no pueden ser nulos";
		if(dosPorUno)
			return calculaDescuentoDosPorUno(pedidos);
		else
			return calculoDescuentoPorCantidad(pedidos);
	}

	/**
	 * Calculo los descuentos dos por uno
	 * pre: pedidos != null
	 * @param pedidos lista de pedidos
	 * @return descuento correspondiente
	 */
	private double calculaDescuentoDosPorUno(ArrayList<Pedido> pedidos){
		assert pedidos != null : "Los pedidos no pueden ser nulos";
		double desc = 0;
		for (Pedido pedido : pedidos){
			if(pedido.getProducto().getId() == producto.getId())
				desc += Math.floor(pedido.getCantidad()/2) * pedido.getProducto().getPrecioVenta();
		}
		return desc;
	}

	/**
	 * Calcula el descueno de decuentos por cantidad
	 * pre: pedidos != null
	 * @param pedidos lista de pedidos
	 * @return decuento correspondiente
	 */
	private double calculoDescuentoPorCantidad(ArrayList<Pedido> pedidos){
		assert pedidos != null : "Los pedidos no pueden ser nulos";
		double desc = 0;
		for (Pedido pedido : pedidos){
			if(pedido.getProducto().getId() == producto.getId() && pedido.getCantidad() >= cantMinima)
				desc += (producto.getPrecioVenta() - precioUnitario) * pedido.getCantidad();
		}
		return desc;
	}

	/**
	 * Retorna la informacion de la promocion en forma de string
	 * @return informacion de la promocion en forma de string
	 */
	@Override
	public String toString() {
		return String.format("| %-12s %-5s  %s |", producto.getNombre(), dosPorUno ? "2x1" : "xCant", dias);
	}

	/**
	 * invariante de clase
	 */
	private void invariante(){
		assert this.dosPorUno | this.dtoPorCant : "Dos por uno y dto por cantidad no pueden ser nulos en simultaneo";
		if(dtoPorCant){
			assert cantMinima >= 0 : "La cantidad minima no puede ser negativa";
			assert precioUnitario >= 0 : "El precio unitario no puede ser negativo";
		}

	}
}
