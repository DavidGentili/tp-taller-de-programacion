package modelo.configEmpresa;

import modelo.persist.OperarioDTO;

import static helpers.OperarioHelpers.correctPassword;

public class Operario {
    private static int nroOperario = 0;
    private int id;
    private String nombreApellido;
    private String nombreUsuario;
    private String password;
    private Boolean activo;

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

//    /**
//     * Retorna un operarioDTO, para su transferencia de informacion
//     * @return OperarioDTO correspondiente
//     */
//    protected OperarioDTO getOperarioDTO(){return null;}
}
