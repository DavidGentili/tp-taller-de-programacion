
package modelo.gestorEmpresa;

import enums.FormasDePago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Clase abstracta que representa una promocion
 */
public abstract class Promocion implements Serializable {
	protected static int nroPromociones = 0;
	protected int id;
	protected String dias;
	protected boolean activa;


	/**
	 * Retorna una instancia de promocion
	 * @param dias dias de la promocion
	 */
	public Promocion(String dias) {
		assert dias != null && !dias.isEmpty() && !dias.isBlank() : "Los dias no pueden ser nulos";

		this.id = nroPromociones;
		nroPromociones++;
		this.dias = dias;
		this.activa = true;

		assert activa == true : "No se asigno correctamente el estado inicial";
		assert this.dias == dias : "No se asigno correctamente los dias";
	}

	/**
	 * Retorna el numero de promociones
	 * @return numero de promociones
	 */
	protected static int getNroPromociones(){
		return nroPromociones;
	}

	/**
	 * Determina el numero de promociones
	 * @param nroPromociones nuevo numero de promociones
	 */
	protected static void setNroPromociones(int nroPromociones){
		assert nroPromociones >= 0 : "El numero de promociones no puede ser negativo";
		Promocion.nroPromociones = nroPromociones;
	}

	/**
	 * Determina el id de la promocion
	 * @param id nuevo id de la promocion
	 */
	public void setId(int id){
		assert id >= 0 : "El id no puede ser negativo";
		this.id = id;

		assert this.id == id : "No se asigno correctamente el id";
	}

	/**
	 * Desactiva la promocion
	 */
	public void desactivarPromocion() {
		this.activa = false;
	 }

	/**
	 * Activa la promocion
	 */
	public void activarPromocion() {
		this.activa = true;
	 }

	/**
	 * Define los dias de promocion
	 * pre: dias no puede ser nulo, vacio o blanco
	 * @param dias : Los dias de promocion
	 */
	public void setDias(String dias) {
		assert dias != null && !dias.isBlank() && !dias.isEmpty() : "Los dias no pueden ser nulos";
		this.dias = dias;
		assert this.dias == dias : "No se asignaron correctamente los dias";
	}

	/**
	 * Retorna el id de la promocion
	 * @return id de la promocion
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retorna los dias de la promocion
	 * @return dias de la promocion
	 */
	public String getDias() {
		return dias;
	}

	/**
	 * Retorna si la promocion esta activa
	 * @return si la promocion esta activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * retorna si la promocion es acumulable
	 * @return
	 */
	public abstract boolean isAcumulable();

	/**
	 * Retorna el descuento
	 * pre: pedidos != null
	 * @param pedidos lista de pedidos
	 * @return descuento correspondiente
	 */
	public abstract double getDescuento(ArrayList<Pedido> pedidos);

	/**
	 * Retorna si se aplica la promocion
	 * pre: pedidos != null
	 * 		formaDePago != null
	 * @param pedidos lista de pedidos
	 * @param formaDePago forma de pago
	 * @return si la promocion es aplicable
	 */
	public abstract boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formaDePago);

	/**
	 * Retorna si la promocion es aplicable
	 * pre: pedidos != null
	 * 		formaDePago != null
	 * 		fecha != null
	 * @param pedidos lista de pedidos
	 * @param formasDePago forma de pago
	 * @param fecha fecha de la compra
	 * @return si la promociones es aplicable
	 */
	public abstract boolean aplicaPromocion(ArrayList<Pedido> pedidos, FormasDePago formasDePago, GregorianCalendar fecha);

}