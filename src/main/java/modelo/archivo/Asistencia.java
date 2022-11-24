package modelo.archivo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import modelo.configEmpresa.Mozo;


/**
 * Clase que permite registrar el estado de asistencia de cada uno de los mozos
 * @author
 *
 */
public class Asistencia implements Serializable {
    private GregorianCalendar fecha;
    private Mozo mozo;
    private String estado;
    
    
    /**
     * Registra el estado de asistencia del mozo en el día de la fecha
     * @param mozo del cual se desea registrar el estado
     * @param estado en el que se encuentra el mozo (Activo, De Franco o Ausente) //Ver Enums
	 * @param fecha : fecha de la asistencia
     * pre: Mozo debe ser distinto de Null y el estado cumplir con los Strings creados como "enums"
     * post: genera el registro de asistencia de un mozo para el día de la fecha
     */
    public Asistencia(Mozo mozo, String estado, GregorianCalendar fecha){
		assert  mozo != null : "El mozo no puede ser nulo";
		assert  estado != null && !estado.isBlank() && !estado.isEmpty() : "El estado no puede ser nulo";

		this.fecha = fecha;
		this.mozo = mozo;
		this.estado = estado;
	}
    
    /**
     * Consulta la fecha en la cual se realizó el registro
     * @return fecha de registro
     */
	public GregorianCalendar getFecha() {return fecha;}


	/**
	 * Consulta el mozo para el cual se realizó el registro
	 * @return mozo que se desea registrar el estado
	 */
	public Mozo getMozo() {return mozo;}


	
	/**
	 * Consulta el estado del mozo para el cual se generó el registro
	 * @return estado del mozo (Activo, De Franco o Ausente) //Ver Enums
	 */
	public String getEstado() {return estado;}

	/**
	 * Retorna un string con la informacion de la asistencia
	 * @return String con informacion de la asistencia
	 */
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return String.format("Fecha: %s Mozo:  %-5d %-30s %s", sdf.format(fecha.getTime()), mozo.getId(), mozo.getNombreApellido(), estado);
	}
    
}
