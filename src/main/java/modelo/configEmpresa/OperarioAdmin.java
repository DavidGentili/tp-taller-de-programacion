package modelo.configEmpresa;

import exceptions.ContraseñaIncorrectaException;
import modelo.persist.OperarioDTO;

public class OperarioAdmin extends Operario{

    /**
     * Se encarga de crear un opeario nuevo si la contraseña es valida, este operario es activo
     *
     * @param nombreApellido : nombre y apellido
     * @param nombreUsuario  : nombre de usuario
     * @param password       : contraseña
     * @throws ContraseñaIncorrectaException : Si la contraseña no cumple con los requisitos de la documentacion
     * pre: nombreApellido != null && nombreApellido != ""
     *      nombreUsuario != null && nombreUsuruaio != ""
     * post: se retorna un operario nuevo activo
     */
    public OperarioAdmin(String nombreApellido, String nombreUsuario, String password) throws ContraseñaIncorrectaException {
        super(nombreApellido, nombreUsuario, password);
    }

    /**
     * Informa si el operario esta calificado para gestionar mesas
     * @return posibilidad de gestionar mesas
     */
    @Override
    public boolean puedeGestionarMesas(){return true;}

    /**
     * Informa si el operario esta calificado para gestioanr mozos
     * @return posibilidad de gestionar mozos
     */
    @Override
    public boolean puedeGestionarMozos(){return true;}

    /**
     * Informa si el operario esta calificado para gestionar productos
     * @return posibilidad de gestinar productos
     */
    @Override
    public boolean puedeGestionarProductos(){return true;}

    /**
     * Informa si el operario esta calificado para gestionar sueldo
     * @return posibilidad de gestionar sueldo
     */
    @Override
    public boolean puedeGestionarSueldo(){return true;}

    /**
     * Informa si el operario esta claificado para gestionar operarios
     * @return posibilidad de gestionar operarios
     */
    @Override
    public boolean puedeGestionarOperarios(){return true;}

    /**
     * Retorna un operarioDTO, para su transferencia de informacion
     * @return OperarioDTO correspondiente
     */
    @Override
    protected OperarioDTO getOperarioDTO(){return null;}
}
