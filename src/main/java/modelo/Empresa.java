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
    }

    public void guardarEstado(){
        try{
            gestorEmpresa.guardarEmpresa();
            configuracion.guardarConfiguracion();
            archivo.almacenarArchivo();
        } catch (ArchivoNoInciliazadoException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected GestorEmpresa getGestorEmpresa() {
        return gestorEmpresa;
    }

    protected ConfiguracionEmpresa getConfiguracion() {
        return configuracion;
    }

    protected Archivo getArchivo() {
        return archivo;
    }

    protected Operario getUsuario(){
        return usuario;
    }

    protected void setUsuario(Operario user){
        usuario = user;
        notifyObservers();
    }

    protected void setState(StateEmpresa state){
        this.state = state;
        setChanged();
        notifyObservers();
    }

    public boolean requiereCambioContraseña(){
        return !usuario.isChangePassword();
    }

    public boolean isLogin(){
        return usuario != null;
    }


    /**
     * Obtiene el nombre del local
     *
     * @return nombre del local;
     */
    public String getNombreLocal() throws UsuarioNoLogueadoException {
        return state.getNombreLocal();
    }

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     *
     * @param name nuevo nombre;
     */
    public void cambiarNombreLocal(String name) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        state.cambiarNombreLocal(name);
        setChanged();
        notifyObservers();
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
     *
     * @param sueldo : sueldo de la empresa
     */
    public void setSueldo(Sueldo sueldo) throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.setSueldo(sueldo);
        setChanged();
        notifyObservers();
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
     *
     * @param nuevo nuevo mozo
     */
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarMozo(nuevo);
        setChanged();
        notifyObservers();
    }

    /**
     * Actualiza un mozo
     * pre: actualizado != null
     * mozoId >= 0
     *
     * @param actualizado : mozo con los valores actualizados
     * @param mozoId      : id del mozo a modificar
     */
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarMozo(actualizado, mozoId);
        setChanged();
        notifyObservers();
    }

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     *
     * @param mozoId : id del mozo
     */
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarMozo(mozoId);
        setChanged();
        notifyObservers();
    }

    /**
     * Cambia el estado de un mozo
     *
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     */
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cambiarEstadoMozo(mozoId, estado);
        setChanged();
        notifyObservers();
    }

    /**
     * Retorna las mesas de la empresa
     *
     * @return mesas de la empresa
     */
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException {
        return state.getMesas();
    }

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     *
     * @param nueva : mesa a agregar
     */
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarMesa(nueva);
        setChanged();
        notifyObservers();
    }

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     * mesaId >= 0
     *
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa     : nro de la mesa a actualizar
     */
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarMesa(actualizada, nroMesa);
        setChanged();
        notifyObservers();
    }

    /**
     * elimina una mesa con el numero correspondiente
     * pre: mesa >= 0;
     *
     * @param nroMesa : numero de la mesa a liminar
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
     *
     * @param nuevo nuevo producto de la empresa
     */
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarProducto(nuevo);
        setChanged();
        notifyObservers();
    }

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     * idProducto >= 0
     *
     * @param actualizado : producto con los valores actualzados
     * @param idProducto  : id del producto a actualizar
     */
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizaProducto(actualizado, idProducto);
        setChanged();
        notifyObservers();
    }

    /**
     * elimina un producto
     * pre id >= 0
     *
     * @param idProducto id del producto a eliminar
     */
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarProducto(idProducto);
        setChanged();
        notifyObservers();
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
     *
     * @param nuevo operario nuevo
     */
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarOperario(nuevo);
        setChanged();
        notifyObservers();
    }

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     * idOperario >= 0
     *
     * @param actualizado : operario con los valores actualizados
     * @param idOperario  : id del operario
     */
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarOperario(actualizado, idOperario);
        setChanged();
        notifyObservers();
    }

    /**
     * Cambia la contraseña de un usuario usuario
     *
     * @param password    Contraseña actual
     * @param newPassword
     * @param idOperario  id del operario
     * @throws OperarioNoEncontradoException  si no se encuentra el operario con dicho id
     * @throws ContraseniaIncorrectaException si la nueva contraseña no cumple con el formato
     * @throws UsuarioNoAutorizadoException   si la actual contraseña no coincide
     */
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException {
        state.cambiarContraseniaOperario(password, newPassword, idOperario);
        setChanged();
        notifyObservers();
    }

    /**
     * elimina al operario que corresponde con el id
     * pre : idOperario >= 0
     *
     * @param idOperario : id del operario a eliminar
     */
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, EliminarOperarioLogueadoException {
        state.eliminarOperario(idOperario);
        setChanged();
        notifyObservers();
    }

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     * password != null !password.isBlack !password.isEmpty
     *
     * @param userName Nombre de ususario
     * @param password Contraseña
     */
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException {
        state.login(userName, password);
        setChanged();
        notifyObservers();
    }

    /**
     * Cierra la sesion del ususario actual
     */
    public void logout() throws UsuarioNoLogueadoException {
        state.logout();
        setChanged();
        notifyObservers();
    }

    /**
     * Abre la empresa
     */
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.abrirEmpresa();
        setChanged();
        notifyObservers();
    }

    /**
     * Cierra la empresa
     */
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cerrarEmpresa();
        setChanged();
        notifyObservers();
    }

    /**
     * Retorna las asignaciones activas de mozos con mesas
     *
     * @return asignaciones mozo mesa
     */
    public ArrayList<MozoMesa> getAsignacionMozoMeza() throws UsuarioNoLogueadoException {
        return state.getAsignacionMozoMeza();
    }

    /**
     * Asigna un mozo a una mesa
     *
     * @param mozoId  id del mozo
     * @param nroMesa nro de mesa
     * @param fecha   fecha de la asignacion
     */
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.asignaMozo(mozoId, nroMesa, fecha);
        setChanged();
        notifyObservers();
    }

    /**
     * Elimina una relacion de mozo con mesa
     *
     * @param nroMesa nro de mesa
     */
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarRelacionMozoMeza(nroMesa);
        setChanged();
        notifyObservers();
    }

    /**
     * retorna las comandas activas
     *
     * @return comandas activas
     */
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException {
        return state.getComandas();
    }

    /**
     * agrega una comanda asignandola a una mesa
     *
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     */
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, MesaNoEncontradaException {
        state.agregarComanda(nroMesa);
        setChanged();
        notifyObservers();
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     *
     * @param nroMesa numero de la mesa
     * @param pago    tipo de  pago
     */
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cerrarComanda(nroMesa, pago);
        setChanged();
        notifyObservers();
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     */
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, ProductoNoEncontradoException {
        state.agregarPedido(nroMesa,  pedido);
        setChanged();
        notifyObservers();
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param idProducto  id del producto pedido
     * @param cantidad cantidad de productos del pedido
     */
    public void agregarPedido(int nroMesa, int idProducto, int cantidad) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, ProductoNoEncontradoException {
        Producto prod = configuracion.getProductoById(idProducto);
        if(prod == null)
            throw new ProductoNoEncontradoException("El producto ingresado no existe");
        Pedido pedido = new Pedido(prod, cantidad);
        state.agregarPedido(nroMesa,  pedido);
        setChanged();
        notifyObservers();
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
     *
     * @param promo promocion de prodcuto a agregar
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarPromocionProducto(promo);
        setChanged();
        notifyObservers();
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     *
     * @param promo : promocion temporal a agregar
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarPromocionTemp(promo);
        setChanged();
        notifyObservers();
    }

    /**
     * Elimina una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a eliminar
     */
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarPromocion(id);
        setChanged();
        notifyObservers();
    }

    /**
     * Activa una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a activar
     */
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.activarPromocion(id);
        setChanged();
        notifyObservers();
    }

    /**
     * Desactiva una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a desactivar
     */
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.desactivarPromocion(id);
        setChanged();
        notifyObservers();
    }

    /**
     * Retorna una lista con las facturas generadas
     *
     * @return lista de facturas
     */
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException {
        return state.getFacturas();
    }

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     *
     * @param factura factura a agregar
     */
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarFactura(factura);
        setChanged();
        notifyObservers();
    }

    /**
     * Retorna las comandas archivadas
     *
     * @return comandas archivadas
     */
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException {
        return state.getComandasArchivadas();
    }

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     *
     * @return relaciones archivadas
     */
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException {
        return state.getAsignacionMozoMesaArchivadas();
    }

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     *
     * @return coleccion de ventas por mesa
     */
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException {
        return state.calculaPromedioPorMesa();
    }

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     *
     * @return coleccion de ventas por mozo
     */
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException {
        return state.calculaEstadisticasMozo();
    }

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     *
     * @return informacion de ventas del mozo que mas vendio
     */
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMayorVolumenDeVentas();
    }

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     *
     * @return informacion de ventas del mozo que menos vendio
     */
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMenorVolumenDeVentas();
    }

    public ArrayList<Asistencia> getAsistencia(){
        return archivo.getRegistroDeAsistencia();
    }

    public int getIdUsuario() throws UsuarioNoLogueadoException {
        return state.getIdUsuario();
    }

}
