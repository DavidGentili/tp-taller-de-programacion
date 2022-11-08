package modelo.empresa;

public class PromocionTemp extends Promocion {
	private String formapago;
	private int porcentajedto;
	private boolean acumulable;
	private String nombre;
	
	
	
	public PromocionTemp(int idprom, String dias, String formapago, int porcentajedto, boolean acumulable,
			String nombre) {
		super(idprom, dias);
		this.formapago = formapago;
		this.porcentajedto = porcentajedto;
		this.acumulable = acumulable;
		this.nombre = nombre;
	}
	public int getPorcentajedto() {
		return porcentajedto;
	}
	public boolean isAcumulable() {
		return acumulable;
	}
	public String getNombre() {
		return nombre;
	}
	public String getFormapago() {
		return formapago;
	}
	
	
	
	
	
	
}
