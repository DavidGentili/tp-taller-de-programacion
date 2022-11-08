
package modelo.empresa;

public abstract class Promocion {
 private int idprom;
 private String dias;
 private boolean activa;

 

 public Promocion(int idprom, String dias) {
	super();
	this.idprom = idprom;
	this.dias = dias;
	this.activa = true;
}
public void DesactivarPromocion() {
	 
 }
public void ActivarPromocion() {
	 
 }



public int getIdprom() {
	return idprom;
}
public String getDias() {
	return dias;
}
public boolean isActiva() {
	return activa;
}
 
 
}