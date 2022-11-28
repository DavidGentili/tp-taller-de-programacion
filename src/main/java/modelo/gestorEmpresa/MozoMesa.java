package modelo.gestorEmpresa;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MozoMesa implements Serializable {
    private GregorianCalendar fecha;
    private Mozo mozo;
    private Mesa mesa;

	/**
	 * genera una instancia de asignacion mozo con mesa
	 * pre: fecha != null
	 * 		mesa != null
	 * 		mozo != null
	 * @param fecha fecha de la asignacion
	 * @param mozo mozo a asignar
	 * @param mesa mesa a asignar
	 */
	public MozoMesa(GregorianCalendar fecha, Mozo mozo, Mesa mesa) {
		assert mesa != null : "La mesa no puede ser nula";
		assert mozo != null : "El mozo no puede ser nulo";
		assert fecha != null : "La fecha no puede ser nula";

		this.fecha = fecha;
		this.mozo = mozo;
		this.mesa = mesa;

		invariante();
		assert this.fecha.equals(fecha) : "No se asigno correctamente la fecha";
		assert this.mesa == mesa : "No se asigno correctamente la mesa";
		assert this.mozo == mozo : "NO se asigno correctamente el mozo";
	}

	/**
	 * Retorna la fecha de la asignacion
	 * @return fecha de la asignacion
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * Retorna el mozo de la asignacion
	 * @return mozo de la asignacion
	 */
	public Mozo getMozo() {
		return mozo;
	}

	/**
	 * Retorna la mesa de la asignacion
	 * @return mesa de la asignacion
	 */
	public Mesa getMesa() {
		return mesa;
	}

	/**
	 * Retorna la informacion de la asignacion en forma de string
	 * @return informacion de la asisgnacion en forma de string
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return String.format("%-10s %-15s %-3d", sdf.format(fecha.getTime()), mozo.getNombreApellido(), mesa.getNroMesa());
	}

	/**
	 * Invariante de clase
	 */
	private void invariante(){
		assert mesa != null : "La mesa no puede ser nula";
		assert mozo != null : "El mozo no puede ser nulo";
		assert fecha != null : "La fecha no puede ser nula";
	}
}
