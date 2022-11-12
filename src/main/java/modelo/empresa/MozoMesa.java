package modelo.empresa;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;

import java.util.Date;
import java.util.GregorianCalendar;

public class MozoMesa {
    private GregorianCalendar fecha;
    private Mozo mozo;
    private Mesa mesa;
    
    
	public MozoMesa(GregorianCalendar fecha, Mozo mozo, Mesa mesa) {
		super();
		this.fecha = fecha;
		this.mozo = mozo;
		this.mesa = mesa;
	}


	public GregorianCalendar getFecha() {
		return fecha;
	}


	public Mozo getMozo() {
		return mozo;
	}


	public Mesa getMesa() {
		return mesa;
	}
    
    
}
