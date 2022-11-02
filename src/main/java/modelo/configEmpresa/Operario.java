package modelo.configEmpresa;

import exceptions.ContraseñaIncorrectaException;

public class Operario {
    private static int nroOperario = 0;
    private int id_operario;
    private String nombreApellido;
    private String nombreUsuario;
    private String password;
    private Boolean activo;

    /**
     * Se encarga de crear un opeario nuevo si la contraseña es valida, este operario es activo
     * @param nombreApellido : nombre y apellido
     * @param nombreUsuario : nombre de usuario
     * @param password : contraseña
     * @throws ContraseñaIncorrectaException : Si la contraseña no cumple con los requisitos de la documentacion
     * pre: nombreApellido != null && nombreApellido != ""
     *      nombreUsuario != null && nombreUsuruaio != ""
     * post: se retorna un operario nuevo activo
     */
    public Operario(String nombreApellido, String nombreUsuario, String password) throws ContraseñaIncorrectaException {}

    /**
     * Define un numero de operarios
     * @param nroOperario
     */
    protected static void setNroOperario(int nroOperario){}

    /**
     * Retorna el numero de operarios
     * @return Numero de operarios
     */
    protected static int getNroOperario(){return 0;}

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
}
