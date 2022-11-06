package modelo.empresa;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;

import java.util.Date;

public class MozoMesa {
    private Date fecha;
    private Mozo mozo;
    private Mesa mesa;
    
    
	public MozoMesa(Date fecha, Mozo mozo, Mesa mesa) {
		super();
		this.fecha = fecha;
		this.mozo = mozo;
		this.mesa = mesa;
	}


	public Date getFecha() {
		return fecha;
	}


	public Mozo getMozo() {
		return mozo;
	}


	public Mesa getMesa() {
		return mesa;
	}
    
    
}
