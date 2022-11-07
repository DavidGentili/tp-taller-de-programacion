package modelo.archivo;

import java.util.Date;

import modelo.configEmpresa.Mozo;


/**
 * Clase que permite registrar el estado de asistencia de cada uno de los mozos
 * @author
 *
 */
public class Asistencia {
    private Date fecha;
    private Mozo mozo;
    private String estado;
    
    
    /**
     * Registra el estado de asistencia del mozo en el día de la fecha
     * @param mozo del cual se desea registrar el estado
     * @param estado en el que se encuentra el mozo (Activo, De Franco o Ausente) //Ver Enums
     * pre: Mozo debe ser distinto de Null y el estado cumplir con los Strings creados como "enums"
     * post: genera el registro de asistencia de un mozo para el día de la fecha
     */
    public Asistencia(Mozo mozo, String estado){}

    
    /**
     * Consulta la fecha en la cual se realizó el registro
     * @return fecha de registro
     */
	public Date getFecha() {return fecha;}

	
	/**
	 * Se puede setear la fecha en caso de que no se haya registrado en el día correspondiente
	 * @param fecha de registro
	 * pre: fecha debe ser distinto de null y tener un formato de fecha válido
	 * post: cambia la fecha de la instancia
	 */
	public void setFecha(Date fecha) {this.fecha = fecha;}


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
 
    
}
