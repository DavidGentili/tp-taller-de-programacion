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

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		return String.format("%-10s | %-15s | %-3d", sdf.format(fecha.getTime()), mozo.getNombreApellido(), mesa.getNroMesa());
	}
}
