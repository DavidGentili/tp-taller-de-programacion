
package modelo.gestorEmpresa;

public abstract class Promocion {
	protected static int nroPromociones = 0;
	protected int id;
	protected String dias;
	protected boolean activa;


	public Promocion(String dias) {
		assert dias != null && !dias.isEmpty() && !dias.isBlank() : "Los dias no pueden ser nulos";
		this.id = nroPromociones;
		nroPromociones++;
		this.dias = dias;
		this.activa = true;
	}

	public void desactivarPromocion() {
		this.activa = false;
	 }

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
		assert this.dias == dias;
	}

	public int getId() {
		return id;
	}
	public String getDias() {
		return dias;
	}
	public boolean isActiva() {
		return activa;
	}


}