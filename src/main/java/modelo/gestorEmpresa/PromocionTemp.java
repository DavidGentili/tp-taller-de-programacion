package modelo.gestorEmpresa;

import enums.FormasDePago;
import helpers.FacturaHelpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PromocionTemp extends Promocion implements Serializable {
	private FormasDePago formaPago;
	private int porcentajeDto;
	private boolean acumulable;
	private String nombre;


	/**
	 * Crea una instancia de una promocion temporal
	 * pre: dias != null && !dias.empty && !dias.blank
	 * 		formaPago != null
	 * 		nombre != null && !nombre.empty && !nombre.blank
	 * 		porcentajeDto > 0
	 * @param dias dias de la promocion
	 * @param formaPago forma de pago
	 * @param porcentajeDto porcentaje de descuento
	 * @param acumulable Si es acumulable
	 * @param nombre nombre de la promocion
	 */
	public PromocionTemp( String dias, FormasDePago formaPago, int porcentajeDto, boolean acumulable, String nombre) {
		super(dias);
		assert formaPago != null : "La forma de pago debe ser distinta de null";
		assert nombre != null && !nombre.isEmpty() && !nombre.isBlank() : "El nombre no puede ser nulo, ni vacio";
		assert porcentajeDto > 0 : "El porcentaje de descuento debe ser entero positivo";
		this.formaPago = formaPago;
		this.porcentajeDto = porcentajeDto;
		this.acumulable = acumulable;
		this.nombre = nombre;

		assert this.dias.equals(dias) : "No se asigno correctamente los dias";
		assert this.porcentajeDto == porcentajeDto : "No se asigno correctamente el porcentaje de descuento";
		assert this.formaPago.equals(formaPago) : "No se asigno correctamente la forma de pago";
		assert this.nombre.equals(nombre) : "No se asigno correctamente el nombre";
		assert this.acumulable == acumulable : "No se asigno correctamente si la promocion es acumulable";
	}

	/**
	 * Determina la forma de pago de la promocion
	 * pre : formaPago != null
	 * @param formaPago forma de pago de la promocion
	 */
	public void setFormaPago(FormasDePago formaPago) {
		assert formaPago != null : "La forma de pago no puede ser nula";
		this.formaPago = formaPago;
		assert this.formaPago.equals(formaPago) : "No se asigno correctamente la forma de pago";
	}

	/**
	 * Determina el porcentaje de descuento
	 * pre : PorcentajeDto > 0
	 * @param porcentajeDto porcentaje de descuento
	 */
	public void setPorcentajeDto(int porcentajeDto) {
		assert porcentajeDto > 0 : "El porcentaje de descuento debe ser entero positivo";
		this.porcentajeDto = porcentajeDto;
		assert this.porcentajeDto == porcentajeDto : "No se asigno correctamente el porcentaje de descuento";
	}

	/**
	 * Determina si la promocion es acumulable
	 * @param acumulable si la promocion es acumulable
	 */
	public void setAcumulable(boolean acumulable) {
		this.acumulable = acumulable;
		assert this.acumulable == acumulable : "No se asigno correctamente si la promocion es acumulable";
	}

	/**
	 * Determina el nombre de la promocion
	 * pre : nombre != null && !nombre.empty && !nombre.blank
	 * @param nombre nombre de la promocion
	 */
	public void setNombre(String nombre) {
		assert nombre != null && !nombre.isEmpty() && !nombre.isBlank() : "El nombre no puede ser nulo, ni vacio";
		this.nombre = nombre;
		assert this.nombre.equals(nombre) : "No se asigno correctamente el nombre";
	}

	/**
	 * Retorna el porcentaje de descuento
	 * @return porcentaje de descuento
	 */
	public int getPorcentajeDto() {
		return porcentajeDto;
	}

	/**
	 * Retorna si la promocion es acumulable
	 * @return si la promocion es acumulable
	 */
	public boolean isAcumulable() {
		return acumulable;
	}

	/**
	 * Retorna el descuento de la promocion apartir de una lista de pedidos
	 * pre: pedidos != null
	 * @param pedidos lista de pedidos
	 * @return el descuento de la promocion
	 */
	@Override
	public double getDescuento(ArrayList<Pedido> pedidos) {
		assert pedidos != null : "La lista de pedidos no puede ser nula";
		double total = 0;
		for( Pedido pedido : pedidos)
			total += pedido.getCantidad() * pedido.getProducto().getPrecioVenta();
		return total * porcentajeDto / 100;
	}

	/**
	 * Retorna el nombre de la promocion
	 * @return nombre de la promocion
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Retorna la forma de pago de la promocion
	 * @return forma de pago de la promocion
	 */
	public FormasDePago getFormaPago() {
		return formaPago;
	}

	/**
	 * Retorna si la promocion aplica o no segun una lista de pedidos y una forma de pago
	 * @param pedidos lista de pedidos
	 * @param formaDePago forma de pago
	 * @return si la promocion aplica o no
	 */
	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago) {
		return FacturaHelpers.correspondeDia(dias) && this.formaPago == formaDePago;
	}

	/**
	 * Retorna si la promocion aplica o no segun una lista de pedidos, una forma de pago y una fecha
	 * @param pedidos lista de pedidos
	 * @param formaDePago forma de pago
	 * @param fecha fecha de la compra
	 * @return si la promocion aplica o no
	 */
	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago, GregorianCalendar fecha){
		return FacturaHelpers.correspondeDia(dias, fecha) && this.formaPago == formaDePago;
	}

	/**
	 * Retorna la informacion de la promocion en forma de string
	 * @return informacion de la promocion en forma de string
	 */
	@Override
	public String toString() {
		return String.format("| %-12s  %-10s  %s |", nombre, formaPago.toString(), dias);
	}
}
