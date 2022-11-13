package modelo.configEmpresa;

import enums.EstadoMozos;
import exceptions.*;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaExistenteException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.*;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;

import java.io.IOException;
import java.util.ArrayList;

public class ConfiguracionEmpresa {
    private static ConfiguracionEmpresa instance = null;
    private String nombreLocal = "";
    private GestorDeMozos mozos;
    private GestorDeMesas mesas;
    private GestorDeProductos productos;
    private GestorDeOperarios operarios;
    private Sueldo sueldo;

    private ConfiguracionEmpresa(){
        this.productos = new GestorDeProductos();
        this.mozos = new GestorDeMozos();
        this.mesas = new GestorDeMesas();
        this.operarios = new GestorDeOperarios();
        this.sueldo = null;
    }

    /**
     * Retorna la instancia unica de configuracion empresa
     * @return instancia de configuracion empresa
     */
    public static ConfiguracionEmpresa getInstance(){
        if(instance == null)
            instance = new ConfiguracionEmpresa();
        return instance;
    }

    //PERSISTENCIA

    /**
     * Se encarga de guardar la configuracion de la empresa
     * (faltan agregar las excepciones correspondientes)
     */
    public void guardarConfiguracion() throws ArchivoNoInciliazadoException, IOException {
        ConfiguracionEmpresaDTO persitencia = new ConfiguracionEmpresaDTO(this);
        persitencia.guardarConfiguaracion();
    }

    /**
     * Se encarga de recuperar la configuracion de la empresa
     * (faltan agregar las excepciones correspondientes)
     */
    public void recuperarConfiguracion() throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException {
        ConfiguracionEmpresaDTO pers = new ConfiguracionEmpresaDTO();
        pers.recuperarConfiguracion();
        Operario.setNroOperario(pers.getNroOperarios());
        Mozo.setNroMozos(pers.getNroMozos());
        Producto.setNroProducto(pers.getNroProductos());
        operarios.setOperarios(pers.getOperarios());
        mesas.setMesas(pers.getMesas());
        mozos.setMozos(pers.getMozos());
        productos.setProductos(pers.getProductos());
        this.nombreLocal = pers.getNombreLocal();
        this.sueldo = pers.getSueldo();

    }


    //NOMBRE LOCAL

    /**
     * Se encarga de cambiar el nombre del local, para esto el usuario debe ser admin,
     * en caso de no serlo debe emitir una excepcion
     * @param name : Nuevo nombre del local
     * @param user : usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * pre: El nombre debe ser un string distinto de nulo y de vacio.
     * post: nombreLocal = name || new UsuarioNoAutorizadoException
     *
     */
    public void cambiaNombreLocal(String name, Operario user) throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException {
        assert name != null && !name.isBlank() && !name.isEmpty() : "El nombre debe ser distinto de nulo, blanco y vacio";
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeModificarNombreLocal())
            throw new UsuarioNoAutorizadoException();
        this.nombreLocal = nombreLocal;

        assert this.nombreLocal == nombreLocal : "No se asigno correctamente el nombre del local";

    }

    /**
     * Retorna el nombre del local
     * @return Nombre del local
     */
    public String getNombreLocal(){
        return this.nombreLocal;
    }

    //SUELDO

    /**
     * Retorna el elemento sueldo de la empresa
     * @return El elemento sueldo de la empresa
     */
    public Sueldo getSueldo() { return sueldo;};

    /**
     * Determina el elemento sueldo de la empresa
     * @param nuevoSueldo : El nuevo elemento sueldo
     * @throws UsuarioNoAutorizadoException : Si el usuario no se encuentra autorizado
     *
     * pre: nuevoSueldo != null;
     * post : this.sueldo == nuevoSueldo;
     */
    public void setSueldo(Sueldo nuevoSueldo, Operario user) throws UsuarioNoAutorizadoException {
        assert nuevoSueldo != null : "El sueldo no puede ser nulo";

        if(!user.puedeGestionarSueldo())
            throw new UsuarioNoAutorizadoException();
        this.sueldo = nuevoSueldo;

        assert sueldo == nuevoSueldo : "No se asigno correctamente el nuevo sueldo";
    }

    //MOZOS

    /**
     * Retorna los mozos del sistema
     * @return Los mozos del sistema
     */
    public ArrayList<Mozo> getMozos() {
        return mozos.getMozos();
    };

    /**
     * Retorna el mozo correspondiente al id ingresado, en caso de que no exista dicho id arroja una excepcion
     * pre : id >= 0
     * @param mozoId : Id del mozo deseado
     * @return el mozo correspondiente al id ingresado
     */
    public Mozo getMozoById(int mozoId){
        return mozos.getMozoById(mozoId);
    }

    /**
     * Se encarga de agregar un nuevo mozo a los registros de la empresa, si el usuario no es admin
     * se emite una excepcion
     * @param nuevoMozo : El nuevo mozo que se desea agregar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws MozoYaAgregadoException : Si el mozo ya se encuentra agregado en la coleccion
     * pre: el nuevo mozo debe ser distinto de null
     *      user != null
     * post: se añadira un nuevo mozo a la coleccion
     */
    public void agregaMozo(Mozo nuevoMozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException {
        mozos.agregaMozo(nuevoMozo,user);
    }

    /**
     * Se encarga de actualizar un mozo, si el usuario no es admin se emite una excepcion
     * @param mozoActualizado : El mozo que se desea actualizar
     * @param mozoId : El usuario que intenta realizar la accion
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * pre: mozoActualizado no debe ser nulo
     *      user != null
     * post: el mozo del sistema tomara los valores de mozoActulizado,
     */
    public void actualizarMozo(Mozo mozoActualizado, int mozoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MozoNoEncontradoException {
        mozos.actualizarMozo(mozoActualizado, mozoId, user);
    };

    /**
     * Se encarga de eliminar un mozo segun su Id
     * @param mozoId : El id del mozo a eleminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws MozoNoEncontradoException : Si el mozo no existe en la coleccion indicada
     * pre: user != null
     * post: Se eliminara el mozo con el id ingresado de la coleccion
     */
    public void eliminaMozo(int mozoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MozoNoEncontradoException {
        mozos.eliminaMozo(mozoId, user);
    };

    /**
     * Cambia el estado de un mozo
     * pre: estado != null
     * @param mozoId Id del mozo
     * @param estado Estado del mozo
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws MozoNoEncontradoException : Si el mozo no es encontrado;
     */
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws IdIncorrectoException, MozoNoEncontradoException {
        mozos.cambiarEstadoMozo(mozoId, estado);
    }

    public void clearEstadoMozos(){
        mozos.clearEstadoMozos();
    }

    //MESAS

    /**
     * Retorna las mesas del sistema
     * @return Las mesas del sistema
     */
    public ArrayList<Mesa> getMesas() {
        return mesas.getMesas();
    };

    /**
     * Retorna la mesa correspondiente al id ingresado, en caso de que no exista una meza con dicho id retorna null
     * pre: nroMesa >= 0;
     * @param nroMesa : numero de la mesa deseado
     * @return la mesa correspondiente al id ingresado
     */
    public Mesa getMesaNroMesa(int nroMesa) {
        return mesas.getMesaNroMesa(nroMesa);
    };

    /**
     * Se agrega una mesa al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevaMesa : La mesa que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws MesaYaExistenteException : Si el numero de mesa ya existe
     * pre: nuevaMesa != null
     *      user != null
     * post: Se agrega la meza a la coleccion de mesas
     */
    public void agregarMesa(Mesa nuevaMesa, Operario user) throws UsuarioNoAutorizadoException, MesaYaExistenteException {
        mesas.agregarMesa(nuevaMesa, user);
    };

    /**
     * Se actualiza la mesa en el sistema
     * @param mesaActualizada : La mesa con los valores actualizados
     * @param nroMesa : el numero de la mesa
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws MesaNoEncontradaException : si no se encuentra la mesa en la coleccion
     * pre: mesaAcutalizada != null
     *      user != null
     * post: Se actualiza la mesa en la coleccion.
     */
    public void actulizarMesa(Mesa mesaActualizada, int nroMesa, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MesaNoEncontradaException{
        mesas.actulizarMesa(mesaActualizada, nroMesa, user);
    };

    /**
     * Se elimina de la colecicon la mesa indicada
     * @param nroMesa : el numero de la mesa a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id no es correcto
     * @throws MesaNoEncontradaException : Si la mesa a eliminar no existe en la coleccion
     * pre: user != null
     * post: Se elimina la mesa de la coleccion
     */
    public void eliminarMesa(int nroMesa, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MesaNoEncontradaException {
        mesas.eliminarMesa(nroMesa, user);
    };

    //PRODUCTOS

    /**
     * Retorna los productos del sistema
     * @return Los productos del sistema
     */
    public ArrayList<Producto> getProductos() {return productos.getProductos();};

    /**
     * Retorna el producto correspondiente al id ingresado, en caso de que no exista un producto con dicho id retorna null
     * pre: productoId >= 0;
     * @param productoId : Id del producto deseado
     * @return el producto correspondiente al id ingresado
     */
    public Producto getProductoById(int productoId){
        return productos.getProductoById(productoId);
    };

    /**
     * Se agrega un producto al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevoProducto : El producto que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws ProductoYaExistenteException : El producto ya se encuentra en la coleccion
     * pre: nuevoProducto != null
     *      user != null
     * post: Se agrega el producto a la coleccion de mesas
     */
    public void agregarProducto(Producto nuevoProducto, Operario user) throws UsuarioNoAutorizadoException, ProductoYaExistenteException {
        productos.agregarProducto(nuevoProducto, user);
    };

    /**
     * Se actualiza un producto en el sistema
     * @param productoActualizado : El producto con los valores actualizados
     * @param productoId : el Id del producto
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws ProductoNoEncontradoException : Si no se encuentra el producto buscado
     * pre: productoActualizado != null
     *      user != null
     * post: Se actualiza el producto en la coleccion.
     */
    public void actulizarProducto(Producto productoActualizado, int productoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, ProductoNoEncontradoException{
        productos.actulizarProducto(productoActualizado, productoId, user);
    };

    /**
     * Se elimina de la colecicon el producto indicada
     * @param idProducto : el id del producto a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si no existe el Id ingresado
     * @throws ProductoNoEncontradoException : Si no se encuentra el producto buscado
     * pre: user != null
     * post: Se elimina el producto de la coleccion
     */
    public void eliminarProducto(int idProducto, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, ProductoNoEncontradoException {
        productos.eliminarProducto(idProducto, user);

    };

    //OPERARIOS

    /**
     * Retorna los operarios del sistema, si el usuario no esta autorizado se emite una excepcion
     * @param user : usuario que intenta realizar dicha accion
     * @return La coleccion de operarios
     * @throws UsuarioNoAutorizadoException : Si el usuario no se encuentra autorizado
     * pre: user != null
     * post: retorna this.operarios
     */
    public ArrayList<Operario> getOperarios(Operario user) throws UsuarioNoAutorizadoException {
        return operarios.getOperarios(user);
    };

    /**
     * Retorna los operarios del sistema
     * @return La coleccion de operarios
     * post: retorna this.operarios
     */
    protected ArrayList<Operario> getOperarios() {
        return operarios.getOperarios();
    }

    /**
     * Se agrega un operario al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevoOperario : el operario que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws OperarioYaExistenteException : Si ya existe un operario con ese id en la estructura
     * pre: nuevoOperario != null
     *      user != null
     * post: Se agrega el operario a la coleccion de mesas
     */
    public void agregarOperario(Operario nuevoOperario, Operario user) throws UsuarioNoAutorizadoException, OperarioYaExistenteException {
        operarios.agregarOperario(nuevoOperario, user);
    };

    /**
     * Se actualiza un operario en el sistema
     * @param operarioActualizado : El operario con los valores actualizados
     * @param idOperario : el Id del operario
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws OperarioNoEncontradoException : Si no se encuentra el operario
     * pre: operarioActualizado != null
     *      user != null
     * post: Se actualiza el operario en la coleccion.
     */
    public void actualizarOperario(Operario operarioActualizado, int idOperario, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, OperarioNoEncontradoException {
        operarios.actualizarOperario(operarioActualizado, idOperario, user);
    };

    /**
     * Se elimina de la colecicon el producto indicada
     * @param idOperario : el id del operario a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id ingresado es incorrecto
     * @throws OperarioNoEncontradoException : Si no se encuentra el operario
     * pre: user != null
     * post: Se elimina el opeario de la coleccion
     */
    public void eliminarOperario(int idOperario, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, OperarioNoEncontradoException {
        operarios.eliminarOperario(idOperario, user);
    };

    /**
     * Si el nombre de usuario y contraseña son correctas, retorna el operario correspondiente
     * @param nombreDeUsuario : Nombre de usuario del operario
     * @param password : Contraseña del operario
     * @return El operario correspondiente con el nombre de usuario.
     * @throws DatosLoginIncorrectosException : Si el nombre de usuario o contraseñas son incorrectos
     * pre: nombreDeUsuario != null && nombreDeUsuario != ""
     *      password != null && password != ""
     * post: retorna el operario deseado.
     */
    public Operario login(String nombreDeUsuario, String password) throws DatosLoginIncorrectosException, OperarioInactivoException {
        return operarios.login(nombreDeUsuario,password);
    }

}
