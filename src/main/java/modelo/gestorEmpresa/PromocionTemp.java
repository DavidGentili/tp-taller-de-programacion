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
	
	
	
	public PromocionTemp( String dias, FormasDePago formaPago, int porcentajeDto, boolean acumulable, String nombre) {
		super(dias);
		this.formaPago = formaPago;
		this.porcentajeDto = porcentajeDto;
		this.acumulable = acumulable;
		this.nombre = nombre;
	}

	public void setFormaPago(FormasDePago formaPago) {
		this.formaPago = formaPago;
	}

	public void setPorcentajeDto(int porcentajeDto) {
		this.porcentajeDto = porcentajeDto;
	}

	public void setAcumulable(boolean acumulable) {
		this.acumulable = acumulable;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPorcentajeDto() {
		return porcentajeDto;
	}
	public boolean isAcumulable() {
		return acumulable;
	}

	@Override
	public double getDescuento(ArrayList<Pedido> pedidos) {
		double total = 0;
		for( Pedido pedido : pedidos)
			total += pedido.getCantidad() * pedido.getProducto().getPrecioVenta();
		return total * porcentajeDto / 100;
	}

	public String getNombre() {
		return nombre;
	}
	public FormasDePago getFormaPago() {
		return formaPago;
	}

	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago) {
		return FacturaHelpers.correspondeDia(dias) && this.formaPago == formaDePago;
	}

	@Override
	public boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago, GregorianCalendar fecha){
		return FacturaHelpers.correspondeDia(dias, fecha) && this.formaPago == formaDePago;
	}

	@Override
	public String toString() {
		return String.format("| %-12s  %-10s  %s |", nombre, formaPago.toString(), dias);
	}
}
