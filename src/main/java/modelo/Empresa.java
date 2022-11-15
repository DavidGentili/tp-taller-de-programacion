package modelo;

import exceptions.operarios.DatosLoginIncorrectosException;
import exceptions.operarios.OperarioInactivoException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import exceptions.operarios.UsuarioNoLogueadoException;
import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Operario;
import modelo.configEmpresa.Sueldo;
import modelo.gestorEmpresa.GestorEmpresa;

public class Empresa {
    private static Empresa instance = null;
    private GestorEmpresa gestorEmpresa;
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private Operario usuario;

    private Empresa(){
        gestorEmpresa = GestorEmpresa.getInstance();
        configuracion = ConfiguracionEmpresa.getInstance();
        archivo = Archivo.getInstance();
        recuperarEstado();
        usuario = null;
    }

    /**
     * Retonar la unica instancia de la empresa
     * @return instancia de la empresa
     */
    public static Empresa getInstance(){
        if(instance == null)
            instance = new Empresa();
        return instance;
    }


    /**
     * Se encarga de recuperar el estado de la empresa, en caso no se posible, la empresa
     * toma los valores iniciales por defecto
     */
    private void recuperarEstado(){
        gestorEmpresa.recuperarEmpresa();
        configuracion.recuperarConfiguracion();
        archivo.recuperarArchivo();
    }

    //LOGIN
    /**
     * Se encarga de iniciar sesion de un usuario
     * @param userName : Nombre de ususario
     * @param password : Contraseña del usuario
     * @throws OperarioInactivoException : Si el operario esta inactivo
     * @throws DatosLoginIncorrectosException : Si los datos no corresponden con ningun usuario
     */
    public void login(String userName, String password) throws OperarioInactivoException, DatosLoginIncorrectosException {
        assert userName != null && !userName.isEmpty() && !userName.isBlank() : "El nombre de ususario debe ser no nulo";
        assert password != null && !password.isEmpty() && !password.isBlank() : "La contraseña debe ser no nula";

        Operario usuario = configuracion.login(userName, password);
        this.usuario = usuario;
    }

    //CONFIGURACION: SUELDO

    /**
     * Retorna el elemento sueldo de la empresa
     * @return elemento sueldo
     */
    public Sueldo getSueldo(){
        return configuracion.getSueldo();
    }

    /**
     * Define el elemento sueldo de la empresa
     * @param sueldo : Elemento sueldo
     * @throws UsuarioNoLogueadoException : Si el usuario no esta logueado
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     */
    public void setSueldo(Sueldo sueldo) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        assert sueldo != null : "El sueldo no puede ser nulo";
        if(usuario == null)
            throw new UsuarioNoLogueadoException("El usuario no ha sido logueado");
        configuracion.setSueldo(sueldo, usuario);
    }

    //CONFIGURACION: NOMBRE LOCAL

    /**
     *
     * @return
     */
    public String getNombreLocal(){
        return configuracion.getNombreLocal();
    }




    /**
     * TAREAS RESTANTES:
     * Conexion GestorEmpresa con Archivo
     * Empresa
     * Controlador
     * Vista
     */


}
