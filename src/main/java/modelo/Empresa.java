package modelo;

import enums.EstadoMozos;
import enums.FormasDePago;
import exceptions.IdIncorrectoException;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.factura.FacturaYaExistenteException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.*;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.*;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;
import exceptions.promociones.PromocionNoEncontradaException;
import exceptions.promociones.PromocionYaExistenteException;
import modelo.archivo.*;
import modelo.configEmpresa.*;
import modelo.gestorEmpresa.*;

import java.io.IOException;
import java.util.*;

/**
 * Clase que representa la empresa
 */
public class Empresa extends Observable {
    private static Empresa instance = null;
    private GestorEmpresa gestorEmpresa;
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private Operario usuario;
    private StateEmpresa state;

    private Empresa(){
        gestorEmpresa = GestorEmpresa.getInstance();
        configuracion = ConfiguracionEmpresa.getInstance();
        archivo = Archivo.getInstance();
        state = new LogoutState(this);
        recuperarEstado();
        usuario = null;
        invariante();
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
        configuracion.recuperarConfiguracion();
        gestorEmpresa.recuperarEmpresa();
        archivo.recuperarArchivo();
        invariante();
    }

    public void guardarEstado(){
        try{
            gestorEmpresa.guardarEmpresa();
            configuracion.guardarConfiguracion();
            archivo.almacenarArchivo();
            invariante();
        } catch (ArchivoNoInciliazadoException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retorna el gestor de la empresa
     * @return gestor de la empresa
     */
    protected GestorEmpresa getGestorEmpresa() {
        return gestorEmpresa;
    }

    /**
     * Retorna el elemento configuracion de la empresa
     * @return Elementeo configuracion de la empresa
     */
    protected ConfiguracionEmpresa getConfiguracion() {
        return configuracion;
    }

    /**
     * Retorna el archivo de la empresa
     * @return Archivo de la empresa
     */
    protected Archivo getArchivo() {
        return archivo;
    }

    /**
     * Retorna el usuario logueado
     * @return Usuario logueado
     */
    protected Operario getUsuario(){
        return usuario;
    }

    /**
     * Determina el usuario logueado
     * @param user usuario que esta logueado
     */
    protected void setUsuario(Operario user){
        usuario = user;
        notifyObservers();
        invariante();
    }

    /**
     * Determina un estado para la empresa
     * @param state estado de la empresa
     */
    protected void setState(StateEmpresa state){
        assert state != null : "El estado de la empresa no puede ser nulo";
        this.state = state;
        setChanged();
        notifyObservers();

        invariante();
        assert this.state == state : "No se asigno correctamente el estado";
    }

    /**
     * Retorna si el usuario requiere cambio de contraseña
     * @return Si el usuario requiere cambio de contraseña
     */
    public boolean requiereCambioContraseña(){
        return !usuario.isChangePassword();
    }

    /**
     * Retorna si hay un usuario logueado
     * @return Si hay un usuario logueado
     */
    public boolean isLogin(){
        return usuario != null;
    }


    /**
     * Obtiene el nombre del local
     * @return nombre del local;
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public String getNombreLocal() throws UsuarioNoLogueadoException {
        return state.getNombreLocal();
    }

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     * @param name nuevo nombre;
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void cambiarNombreLocal(String name) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert name != null && !name.isBlank() && !name.isEmpty() : "El nombre debe ser distinto de nulo y vacio";
        state.cambiarNombreLocal(name);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * retorna el elemento sueldo de la empresa
     *
     * @return elemento sueldo
     */
    public Sueldo getSueldo() throws UsuarioNoLogueadoException {
        return state.getSueldo();
    }

    /**
     * determina el sueldo de la empresa
     * pre: sueldo != null
     * @param sueldo : sueldo de la empresa
     * @throws UsuarioNoAutorizadoException: si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void setSueldo(Sueldo sueldo) throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert sueldo != null : "El sueldo no puede ser nulo";
        state.setSueldo(sueldo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna los mozos de la empresa
     *
     * @return mozos de la empresa
     */
    public ArrayList<Mozo> getMozos() throws UsuarioNoLogueadoException {
        return state.getMozos();
    }

    /**
     * Agrega un mozo a la empresa
     * pre: nuevo != null
     * @param nuevo nuevo mozo
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MozoYaAgregadoException Si el mozo a agregar ya existe
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert nuevo != null : "El mozo no puede ser nulo";
        state.agregarMozo(nuevo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Actualiza un mozo
     * pre: actualizado != null
     *      mozoId >= 0
     * @param actualizado : mozo con los valores actualizados
     * @param mozoId : id del mozo a modificar
     * @throws MozoNoEncontradoException Si no se encontro el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El mozo no puede ser nulo";
        state.actualizarMozo(actualizado, mozoId);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Elimina el mozo correspondiente a la id
     * @param mozoId : id del mozo
     * @throws MozoNoEncontradoException Si no se encontro el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarMozo(mozoId);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Cambia el estado de un mozo
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     * @throws MozoNoEncontradoException Si no se encuentra el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cambiarEstadoMozo(mozoId, estado);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna las mesas de la empresa
     * @return mesas de la empresa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException {
        return state.getMesas();
    }

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     * @param nueva : mesa a agregar
     * @throws MesaYaExistenteException Si la mesa ya existe
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si el usuario no esta autorizado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert nueva != null : "La mesa no puede ser nula";
        state.agregarMesa(nueva);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa : nro de la mesa a actualizar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert actualizada != null : "La mesa no puede ser nula";
        state.actualizarMesa(actualizada, nroMesa);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * elimina una mesa con el numero correspondiente
     * @param nroMesa : numero de la mesa a liminar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarMesa(int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarMesa(nroMesa);
        setChanged();
        notifyObservers();
    }

    /**
     * Retorna los productos de la empresa
     *
     * @return productos de la empresa
     */
    public ArrayList<Producto> getProductos() throws UsuarioNoLogueadoException {
        return state.getProductos();
    }

    /**
     * Agrega un producto a la empresa
     * pre : nuevo != null
     * @param nuevo nuevo producto de la empresa
     * @throws ProductoYaExistenteException Si el producto ya existe
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert nuevo != null : "El producto no puede ser nulo";
        state.agregarProducto(nuevo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     * @param actualizado : producto con los valores actualzados
     * @param idProducto : id del producto a actualizar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El producto no puede ser nulo";
        state.actualizaProducto(actualizado, idProducto);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * elimina un producto
     * @param idProducto id del producto a eliminar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarProducto(idProducto);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * retorna los operarios de la empresa
     *
     * @return operarios de la empresa
     */
    public ArrayList<Operario> getOperarios() throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException {
        return state.getOperarios();
    }

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     * @param nuevo operario nuevo
     * @throws OperarioYaExistenteException Si el operario ya existe
     * @throws UsuarioNoAutorizadoException si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert nuevo != null : "El operario no puede ser nulo";
        state.agregarOperario(nuevo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     * @param actualizado : operario con los valores actualizados
     * @param idOperario : id del operario
     * @throws OperarioNoEncontradoException Si no se encuentra el operario
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException si el ususario no esta autorizado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException si no se cambio la contraseña
     */
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El operario no puede ser nulo";
        state.actualizarOperario(actualizado, idOperario);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Cambia la contraseña de un usuario usuario
     * pre: password != null
     *      newPassword != null
     * @param password Contraseña actual
     * @param newPassword nueva contraseña
     * @param idOperario id del operario
     * @throws OperarioNoEncontradoException si no se encuentra el operario con dicho id
     * @throws ContraseniaIncorrectaException si la nueva contraseña no cumple con el formato
     * @throws UsuarioNoAutorizadoException  si la actual contraseña no coincide
     * @throws IdIncorrectoException Si el id del operario es incorrecto
     */
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, IdIncorrectoException {
        assert password != null : "La contraseña no puede ser nula";
        assert newPassword != null : "la contraseña no puede ser nula";
        state.cambiarContraseniaOperario(password, newPassword, idOperario);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * elimina al operario que corresponde con el id
     * @param idOperario : id del operario a eliminar
     * @throws OperarioNoEncontradoException Si no se encuentra operario
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws EliminarOperarioLogueadoException Si se quiere eliminar el usuario logueado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, EliminarOperarioLogueadoException {
        state.eliminarOperario(idOperario);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     *      password != null !password.isBlack !password.isEmpty
     * @param userName Nombre de ususario
     * @param password Contraseña
     * @throws UsuarioYaLogueadoException Si ya se encuentra logueado un usuario
     * @throws OperarioInactivoException : Si el operario que se quiere iniciar sesion se encuentra inactivo
     * @throws DatosLoginIncorrectosException : Si los datos para el login son incorrectos (nombre de usuario o contraseña)
     */
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException {
        assert userName != null && !userName.isBlank() && !userName.isEmpty() : "El nombre de usuario debe ser distinto de nulo y vacio";
        assert password != null && !password.isBlank() && !password.isEmpty() : "La contraseña debe ser distinto de nulo y vacio";

        state.login(userName, password);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Cierra la sesion del ususario actual
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void logout() throws UsuarioNoLogueadoException {
        state.logout();
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Abre la empresa
     * @throws NoHayMozosAsignadosException Si no hay mozos asignados
     * @throws CantidadMinimaDeProductosEnPromocionException Si no se cumple con la cantidad de productos minimos en promocion
     * @throws CantidadMaximaDeMozosActivosException Si se supera la cantidad maxima de mosos activos
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws CantidadMinimaDeProductosException Si no se supera la cantidad minima de productos
     * @throws CantidadMaximaDeMozosSuperadaException Si se supera la cantidad maxima de mozos
     * @throws CantidadMaximaDeMozosDeFrancoException Si se supera la cantidad maxima de mozos de franco
     * @throws HayMozoSinEstadoAsignadoException Si hay mozos con estados sin asignar
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.abrirEmpresa();
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Cierra la empresa
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws HayComandasActivasException Si hay comandas abiertas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cerrarEmpresa();
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna las asignaciones activas de mozos con mesas
     * @return asignaciones mozo mesa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<MozoMesa> getAsignacionMozoMeza() throws UsuarioNoLogueadoException {
        return state.getAsignacionMozoMeza();
    }

    /**
     * Asigna un mozo a una mesa
     * pre: idMozo >= 0
     * nroMesa >= 0
     * @param mozoId id del mozo
     * @param nroMesa nro de mesa
     * @param fecha fecha de la asignacion
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws MozoNoEncontradoException Si no se encuentra el mozo
     * @throws MozoNoActivoException Si el mozo no esta activo
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert mozoId >= 0 : "El id del mozo no puede ser negativo";
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        state.asignaMozo(mozoId, nroMesa, fecha);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Elimina una relacion de mozo con mesa
     * @param nroMesa nro de mesa
     * @throws MesaNoAsignadaException Si la mesa no esta asignada
     * @throws EmpresaAbiertaException Si la empresa no esta abierta
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarRelacionMozoMeza(nroMesa);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * retorna las comandas activas
     * @return comandas activas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException {
        return state.getComandas();
    }

    /**
     * agrega una comanda asignandola a una mesa
     * pre : nroMesa >= 0
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     * @throws EmpresaCerradaException Si la empresa ya esta cerrada
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws MesaNoEncontradaException si no se encontro la mesa
     */
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, MesaNoEncontradaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        state.agregarComanda(nroMesa);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     * pre : nroMesa >= 0
     *      pago != null
     * @param nroMesa numero de la mesa
     * @param pago tipo de  pago
     * @throws ComandaYaCerradaException Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws MesaNoEncontradaException si no se encontro la mesa
     * @throws MesaYaLiberadaException Si la mesa ya esta liberada
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        assert pago != null : "El metodo de pago no puede ser nulo";
        state.cerrarComanda(nroMesa, pago);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     * pre: nroMesa >= 0
     *      pedido != null
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     * @throws ComandaYaCerradaException      : Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException        : Si la empresa esta cerrada
     * @throws ComandaNoEncontradaException   : SI no se encontro la comanda
     * @throws UsuarioNoLogueadoException     : si el ususario no esta logueado
     * @throws NoSeCambioContraseniaException : Si no se cambio la contraseña
     * @throws ProductoNoEncontradoException  : Si el producto nose encontro
     */
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, ProductoNoEncontradoException {
        assert pedido != null : "El pedido no puede ser nulo";
        assert nroMesa >= 0 : "El nro de meso no puede ser nulo";
        state.agregarPedido(nroMesa,  pedido);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     * pre: nroMesa >= 0
     *      idProducto >= 0
     *      cantidad >= 0
     *
     * @param nroMesa numero de mesa
     * @param idProducto  id del producto pedido
     * @param cantidad cantidad de productos del pedido
     * @throws ComandaYaCerradaException      : Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException        : Si la empresa esta cerrada
     * @throws ComandaNoEncontradaException   : SI no se encontro la comanda
     * @throws UsuarioNoLogueadoException     : si el ususario no esta logueado
     * @throws NoSeCambioContraseniaException : Si no se cambio la contraseña
     * @throws ProductoNoEncontradoException  : Si el producto nose encontro
     */
    public void agregarPedido(int nroMesa, int idProducto, int cantidad) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, ProductoNoEncontradoException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        assert idProducto >= 0 : "El id del producto no puede ser negativo";
        assert cantidad > 0 : "La cantidad debe ser positiva";
        Producto prod = configuracion.getProductoById(idProducto);
        if(prod == null)
            throw new ProductoNoEncontradoException("El producto ingresado no existe");
        Pedido pedido = new Pedido(prod, cantidad);
        state.agregarPedido(nroMesa,  pedido);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna una lista de todas las promociones
     *
     * @return lista de promociones
     */
    public ArrayList<Promocion> getPromociones() throws UsuarioNoLogueadoException {
        return state.getPromociones();
    }

    /**
     * Retorna una lista de las promociones de producto
     *
     * @return lista de promociones de producto
     */
    public ArrayList<PromocionProducto> getPromocionesProducto() throws UsuarioNoLogueadoException {
        return state.getPromocionesProducto();
    }

    /**
     * Retorna una lista de las promociones temporales
     *
     * @return lista de promociones temporales
     */
    public ArrayList<PromocionTemp> getPromocionesTemporales() throws UsuarioNoLogueadoException {
        return state.getPromocionesTemporales();
    }

    /**
     * Agrega una promocion de producto
     * pre: promo != null
     * @param promo promocion de prodcuto a agregar
     * @throws PromocionYaExistenteException Si la promocion ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert promo != null : "La promocion no puede ser nula";
        state.agregarPromocionProducto(promo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     * @param promo : promocion temporal a agregar
     * @throws PromocionYaExistenteException Si la promocion ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert promo != null : "La promocion no puede ser nula";
        state.agregarPromocionTemp(promo);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Elimina una promocion segun un id
     * @param id : id de la promocion a eliminar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarPromocion(id);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Activa una promocion segun un id
     * @param id : id de la promocion a activar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.activarPromocion(id);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Desactiva una promocion segun un id
     * @param id : id de la promocion a desactivar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.desactivarPromocion(id);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna una lista con las facturas generadas
     * @return lista de facturas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException {
        return state.getFacturas();
    }

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     * @param factura factura a agregar
     * @throws FacturaYaExistenteException Si la factura que se quiere agregar ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si todavia no se cambia la contraseña
     */
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        assert factura != null : "La factura no puede ser nula";
        state.agregarFactura(factura);
        setChanged();
        notifyObservers();
        invariante();
    }

    /**
     * Retorna las comandas archivadas
     * @return comandas archivadas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException {
        return state.getComandasArchivadas();
    }

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     * @return relaciones archivadas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException {
        return state.getAsignacionMozoMesaArchivadas();
    }

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     * @return coleccion de ventas por mesa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException {
        return state.calculaPromedioPorMesa();
    }

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     * @return coleccion de ventas por mozo
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException {
        return state.calculaEstadisticasMozo();
    }

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     * @return informacion de ventas del mozo que mas vendio
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMayorVolumenDeVentas();
    }

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     * @return informacion de ventas del mozo que menos vendio
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMenorVolumenDeVentas();
    }

    /**
     * Retrona las asistencias registradas
     * @return Retorna una lista con las asitencias registradas
     */
    public ArrayList<Asistencia> getAsistencia(){
        return archivo.getRegistroDeAsistencia();
    }

    /**
     * Retorna el id del usuario logueado
     * @return id del ususario logueado
     * @throws UsuarioNoLogueadoException : Si el usuario no esta logueado
     */
    public int getIdUsuario() throws UsuarioNoLogueadoException {
        return state.getIdUsuario();
    }

    private void invariante(){
        assert state != null : "El estado no puede ser nulo";
    }

}
