package modelo.configEmpresa;

import exceptions.operarios.ContraseniaIncorrectaException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import helpers.OperarioHelpers;

import java.io.Serializable;

import static helpers.OperarioHelpers.correctPassword;

public class Operario implements Serializable {
    protected static int nroOperario = 0;
    protected int id;
    protected String nombreApellido;
    protected String nombreUsuario;
    protected String password;
    protected Boolean activo;
    protected boolean changePassword;

    /**
     * Se encarga de crear un opeario nuevo si la contraseña es valida, este operario es activo
     * @param nombreApellido : nombre y apellido
     * @param nombreUsuario : nombre de usuario
     * @param password : contraseña
     * pre: nombreApellido != null && nombreApellido != ""
     *      nombreUsuario != null && nombreUsuruaio != "" && nombreUsuario.length <= 10
     *      password != null && password.length <= 12 && password.length > 6
     *      password debe contener al menos una letra mayuscula
     *      password debe contener al menos un numero
     * post: se retorna un operario nuevo activo
     */
    public Operario(String nombreApellido, String nombreUsuario, String password) {
        assert nombreApellido != null && !nombreApellido.isBlank() && !nombreApellido.isEmpty() : "El nombre y apellido no es correcto";
        assert nombreUsuario != null && !nombreUsuario.isEmpty() && !nombreUsuario.isBlank() && nombreUsuario.length() <= 10 : "El nombre de usuario no es correcto";
        assert correctPassword(password) : "La contraseña no es correcta";

        this.nombreApellido = nombreApellido;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.activo = true;
        this.changePassword = false;
        this.id = nroOperario;
        nroOperario++;

        assert this.nombreApellido == nombreApellido : "No se asigno correctamente el nombre del operario";
        assert this.nombreUsuario == nombreUsuario : "No se asigno correctamente el nombre del usuario";
        assert this.password == password : "No se asigno correctamente la contraseña";
        assert this.activo == true : "No se asigno correctamente el valor de activo";
        assert this.id == nroOperario-1 : "No se asigno correctamente el id";

    }

    /**
     * Retorna el Id del operario
     * @return id Operario
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nombre completo del operario
     * @return nombre completo
     */
    public String getNombreApellido() {
        return nombreApellido;
    }

    /**
     * Retorna el nombre de usuario del operario
     * @return nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Retorna si el operario esta activo
     * @return si esta activo el operario
     */
    public Boolean isActivo() {
        return activo;
    }

    /**
     * Define un numero de operarios
     * pre: nroOperario >= 0
     * @param nroOperario
     */
    protected static void setNroOperario(int nroOperario){
        assert nroOperario >= 0 : "No se puede asignar un valor negativo a nroOperario";

        Operario.nroOperario = nroOperario;

        assert Operario.nroOperario == nroOperario : "No se asigno correctamente el numero de operario";
    }

    /**
     * Retorna el numero de operarios
     * @return Numero de operarios
     */
    protected static int getNroOperario(){return Operario.nroOperario;}

    /**
     * Informa si el operario esta calificado para gestionar mesas
     * @return posibilidad de gestionar mesas
     */
    public boolean puedeGestionarMesas(){return false;}

    /**
     * Informa si el operario esta calificado para gestioanr mozos
     * @return posibilidad de gestionar mozos
     */
    public boolean puedeGestionarMozos(){return false;}

    /**
     * Informa si el operario esta calificado para gestionar productos
     * @return posibilidad de gestinar productos
     */
    public boolean puedeGestionarProductos(){return false;}

    /**
     * Informa si el operario esta calificado para gestionar sueldo
     * @return posibilidad de gestionar sueldo
     */
    public boolean puedeGestionarSueldo(){return false;}

    /**
     * Informa si el operario esta claificado para gestionar operarios
     * @return posibilidad de gestionar operarios
     */
    public boolean puedeGestionarOperarios(){return false;}

    /**
     * Informa si el operario esta calificado para gestionar el nombre del local
     * @return posibilidad de gestionar el nombre del local
     */
    public boolean puedeModificarNombreLocal(){return false;}

    /**
     * Actualiza el operario actual con los valores de otro operario
     * @param other Otro operario con los valores actuales
     */
    protected void updateOperario(Operario other){
        this.nombreApellido = other.nombreApellido;
        this.nombreUsuario = other.nombreUsuario;
        this.activo = other.activo;
    }

    /**
     * Retorna si la contraseña coincide con la contraseña ingresada
     * pre : password != null;
     * @param password contraseña ingresada
     * @return si son iguales o no las contraseñas
     */
    protected boolean matchPassword(String password){
        assert password != null;
        return this.password.equals(password);
    }

    /**
     * Cambia la contraseña del usuario
     * @param password Contraseña actual
     * @param newPassword nueva contraseña
     * @throws ContraseniaIncorrectaException si la nueva contraseña no cumple con el formato
     * @throws UsuarioNoAutorizadoException  si la actual contraseña no coincide
     */
    public void cambiarContrasenia(String password, String newPassword) throws ContraseniaIncorrectaException, UsuarioNoAutorizadoException {
        assert password != null;
        assert newPassword != null;
        if(!matchPassword(password))
            throw new UsuarioNoAutorizadoException("La contraseña es incorrecta");
        if(!OperarioHelpers.correctPassword(newPassword))
            throw new ContraseniaIncorrectaException("La nueva contraseña no cumple con los requisitos");
        this.password = newPassword;
        changePassword = true;
    }

    /**
     * Retorna si la contraseña del usuario fue cambiada en algun momento
     * @return si la contraseña del usuario fue cambiada
     */
    public boolean isChangePassword(){
        return changePassword;
    }

    /**
     * Retorna la informacion del operario en forma de string
     * @return informacion del operario en forma de string
     */
    @Override
    public String toString() {
        return String.format("%4d %-12s %-12s", id, nombreApellido, nombreUsuario);
    }
}
