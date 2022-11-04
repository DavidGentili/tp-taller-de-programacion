package modelo.configEmpresa;

import exceptions.EstadoDelMozoIncorrectoException;

import java.io.Serializable;
import java.util.Date;

public class Mozo implements Serializable {
    public static enum Estados {ACTIVO, FRANCO, AUSENTE}
    private static int nroMozos = 0;
    private int id;
    private String nombreApellido;
    private Date fechaNacimiento;
    private int cantHijos;
    private Estados estado;

    /**
     * Se encarga de crear un nuevo mozo en estado activo,
     * @param nombreApellido : Nombre y apellido del mozo
     * @param fechaNacimiento : Fecha de nacimiento del mozo
     * @param cantHijos : cantidad de hijos del mozo
     * pre: nombreApellido != null && nombreApellido != ""
     *      fechaNacimiento != null && el mozo debe ser mayor de 18 años;
     *      cantHijos >= 0
     * post: se creo un nuevo usuario
     */
    public Mozo(String nombreApellido, Date fechaNacimiento, int cantHijos){}

    /**
     * Permite establecer el nro de mozos
     * @param nroMozos : Nro de mozos
     */
    public static void setNroMozos(int nroMozos){}

    /**
     * Retorna el numero de mozos
     * @return : Nro de mozos
     */
    public static int getNroMozos(){return 0;}

    /**
     * Retorna en numero de Id del mozo
     * @return Nro de Id del mozo
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nombre completo del mozo
     * @return Nombre completo del mozo
     */
    public String getNombreApellido() {
        return nombreApellido;
    }

    /**
     * retorna la fecha de nacimiento del mozo
     * @return fecha de nacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * retorna la cantidad de hijos del mozo
     * @return cantidad de hijos del mozo
     */
    public int getCantHijos() {
        return cantHijos;
    }

    public String getEstado(){return null;}

    /**
     * Determina el Id del mozo
     * @param id : nuevo id del mozo
     */
    protected void setId(int id){}

    /**
     * determina un nuevo nombre y apellido del mozo
     * @param nombreApellido : nuevo Nombre y apellido del mozo
     */
    protected void setNombreApellido(String nombreApellido){}

    /**
     * Determina una fecha de nacimiento para el mozo
     * @param fechaNacimiento : Nueva fecha de nacimiento del mozo
     * pre: fechaNacimiento != null && el mozo debe ser mayor de 18
     */
    protected void setFechaNacimiento(Date fechaNacimiento){}

    /**
     * Determina la cantidad de hijos del mozo
     * @param cantHijos : nueva cantidad de hijos del mozo
     * pre: cantHijos > 0
     */
    protected void setCantHijos(int cantHijos){}

    /**
     * Setea el estado correspondiente del mozo
     * @param estado : Nuevo estado del mozo
     * @throws EstadoDelMozoIncorrectoException : Si el estado no pertence a los estados del mozo;
     *
     */
    protected void setEstado(String estado) throws EstadoDelMozoIncorrectoException {};


}
