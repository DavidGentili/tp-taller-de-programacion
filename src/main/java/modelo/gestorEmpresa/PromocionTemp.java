package modelo.gestorEmpresa;

public class PromocionTemp extends Promocion {
	private String formaPago;
	private int porcentajeDto;
	private boolean acumulable;
	private String nombre;
	
	
	
	public PromocionTemp( String dias, String formaPago, int porcentajeDto, boolean acumulable, String nombre) {
		super(dias);
		this.formaPago = formaPago;
		this.porcentajeDto = porcentajeDto;
		this.acumulable = acumulable;
		this.nombre = nombre;
	}

	public void setFormaPago(String formaPago) {
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
	public String getNombre() {
		return nombre;
	}
	public String getFormaPago() {
		return formaPago;
	}
	
	
	
	
	
	
}
