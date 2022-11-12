package modelo.configEmpresa;

import enums.EstadoMozos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import static helpers.FechasHelpers.isOver18;

public class Mozo implements Serializable {
    private static int nroMozos = 0;
    private int id;
    private String nombreApellido;
    private GregorianCalendar fechaNacimiento;
    private int cantHijos;
    private EstadoMozos estado = null;

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
    public Mozo(String nombreApellido, GregorianCalendar fechaNacimiento, int cantHijos){
        assert nombreApellido != null && !nombreApellido.isEmpty() && !nombreApellido.isBlank() : "El nombre debe ser distinto de null, de vacio y de blank";
        assert isOver18(fechaNacimiento) : "El mozo debe tener al menos 18 años";
        assert cantHijos >= 0 : "La cantidad de hijos debe ser mayor a cero";

        this.nombreApellido = nombreApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.cantHijos = cantHijos;
        this.id = nroMozos;
        nroMozos++;

        assert this.nombreApellido == nombreApellido : "No se asigno correctamente el nombre y apellido del mozo";
        assert this.fechaNacimiento == fechaNacimiento : "No se asigno correctamente la fecha de naciminento";
        assert this.cantHijos == cantHijos : "No se asigno correctamente la cantidad de hijos";
        assert this.id == nroMozos - 1 : "No se asigno correctamente el Id"; //Se entiende que no hay una concurrencia, de existir seria recomendable otro metodo de seleccion de id como un hash
    }

    /**
     * Permite establecer el nro de mozos
     * pre: nroMozos >= 0
     * @param nroMozos : Nro de mozos
     */
    protected static void setNroMozos(int nroMozos){
        assert nroMozos >= 0 : "No se puede asignar un numero de mozos negativo";
        Mozo.nroMozos = nroMozos;
        assert Mozo.nroMozos == nroMozos : "No se asigno correctamente el numero de mozos";
    }

    /**
     * Retorna el numero de mozos
     * @return : Nro de mozos
     */
    public static int getNroMozos(){
        return Mozo.nroMozos;
    }

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
    public GregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * retorna la cantidad de hijos del mozo
     * @return cantidad de hijos del mozo
     */
    public int getCantHijos() {
        return cantHijos;
    }

    public EstadoMozos getEstado(){return this.estado;}

    /**
     * Determina el Id del mozo
     * pre: id >= 0;
     * @param id : nuevo id del mozo
     */
    protected void setId(int id){
        assert id >= 0 : "El id no puede ser negativo";
        this.id = id;
        assert this.id == id : "No se asigno correctamente el nro de Id";
    }

    /**
     * determina un nuevo nombre y apellido del mozo
     * pre: nombreApellido != null && nombreApellido != ""
     * @param nombreApellido : nuevo Nombre y apellido del mozo
     */
    protected void setNombreApellido(String nombreApellido){
        assert nombreApellido != null && nombreApellido.isEmpty() && nombreApellido.isBlank() : "El nombre debe ser distinto de null, de vacio y de blank";

        this.nombreApellido = nombreApellido;

        assert this.nombreApellido == nombreApellido : "No se asigno correctamente el nombre y apellido del mozo";
    }

    /**
     * Determina una fecha de nacimiento para el mozo
     * pre: fechaNacimiento != null && el mozo debe ser mayor de 18 años;
     * @param fechaNacimiento : Nueva fecha de nacimiento del mozo
     * pre: fechaNacimiento != null && el mozo debe ser mayor de 18
     */
    protected void setFechaNacimiento(GregorianCalendar fechaNacimiento){
        assert isOver18(fechaNacimiento) : "El mozo debe tener al menos 18 años";

        this.fechaNacimiento = fechaNacimiento;

        assert this.fechaNacimiento == fechaNacimiento : "No se asigno correctamente la fecha de naciminento";
    }

    /**
     * Determina la cantidad de hijos del mozo
     *
     * @param cantHijos : nueva cantidad de hijos del mozo
     * pre: cantHijos > 0
     */
    protected void setCantHijos(int cantHijos){
        assert cantHijos >= 0 : "La cantidad de hijos debe ser mayor a cero";

        this.cantHijos = cantHijos;

        assert this.cantHijos == cantHijos : "No se asigno correctamente la cantidad de hijos";
    }

    /**
     * Setea el estado correspondiente del mozo
     * @param estado : Nuevo estado del mozo
     *
     */
    protected void setEstado(EstadoMozos estado) {
        this.estado = estado;
    };

    protected void updateMozo(Mozo other){
        this.estado = other.estado;
        this.cantHijos = other.cantHijos;
        this.nombreApellido = other.nombreApellido;
        this.fechaNacimiento = other.fechaNacimiento;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
        return String.format("%4d %-12s %-10s %2d %-10s", id, nombreApellido, sdf.format(fechaNacimiento.getTime()), cantHijos, estado);

    }
}
