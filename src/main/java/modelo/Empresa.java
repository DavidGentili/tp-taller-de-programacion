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
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;
import exceptions.promociones.PromocionNoEncontradaException;
import exceptions.promociones.PromocionYaExistenteException;
import modelo.archivo.Archivo;
import modelo.archivo.Factura;
import modelo.archivo.VentasMesa;
import modelo.archivo.VentasMozo;
import modelo.configEmpresa.*;
import modelo.gestorEmpresa.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Empresa implements StateEmpresa{
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
    }

    protected void setState(StateEmpresa state){
        this.state = state;
    }

    public boolean isPrimerAcceso(){
        return configuracion.isPrimerAcceso();
    }


    /**
     * Obtiene el nombre del local
     *
     * @return nombre del local;
     */
    @Override
    public String getNombreLocal() throws UsuarioNoLogueadoException {
        return state.getNombreLocal();
    }

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     *
     * @param name nuevo nombre;
     */
    @Override
    public void cambiarNombreLocal(String name) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        state.cambiarNombreLocal(name);
    }

    /**
     * retorna el elemento sueldo de la empresa
     *
     * @return elemento sueldo
     */
    @Override
    public Sueldo getSueldo() throws UsuarioNoLogueadoException {
        return state.getSueldo();
    }

    /**
     * determina el sueldo de la empresa
     * pre: sueldo != null
     *
     * @param sueldo : sueldo de la empresa
     */
    @Override
    public void setSueldo(Sueldo sueldo) throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.setSueldo(sueldo);
    }

    /**
     * Retorna los mozos de la empresa
     *
     * @return mozos de la empresa
     */
    @Override
    public ArrayList<Mozo> getMozos() throws UsuarioNoLogueadoException {
        return state.getMozos();
    }

    /**
     * Agrega un mozo a la empresa
     * pre: nuevo != null
     *
     * @param nuevo nuevo mozo
     */
    @Override
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarMozo(nuevo);
    }

    /**
     * Actualiza un mozo
     * pre: actualizado != null
     * mozoId >= 0
     *
     * @param actualizado : mozo con los valores actualizados
     * @param mozoId      : id del mozo a modificar
     */
    @Override
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarMozo(actualizado, mozoId);
    }

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     *
     * @param mozoId : id del mozo
     */
    @Override
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarMozo(mozoId);
    }

    /**
     * Cambia el estado de un mozo
     *
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     */
    @Override
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cambiarEstadoMozo(mozoId, estado);
    }

    /**
     * Retorna las mesas de la empresa
     *
     * @return mesas de la empresa
     */
    @Override
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException {
        return state.getMesas();
    }

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     *
     * @param nueva : mesa a agregar
     */
    @Override
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarMesa(nueva);
    }

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     * mesaId >= 0
     *
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa     : nro de la mesa a actualizar
     */
    @Override
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarMesa(actualizada, nroMesa);
    }

    /**
     * elimina una mesa con el numero correspondiente
     * pre: mesa >= 0;
     *
     * @param nroMesa : numero de la mesa a liminar
     */
    @Override
    public void eliminarMesa(int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarMesa(nroMesa);
    }

    /**
     * Retorna los productos de la empresa
     *
     * @return productos de la empresa
     */
    @Override
    public ArrayList<Producto> getProductos() throws UsuarioNoLogueadoException {
        return state.getProductos();
    }

    /**
     * Agrega un producto a la empresa
     * pre : nuevo != null
     *
     * @param nuevo nuevo producto de la empresa
     */
    @Override
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarProducto(nuevo);
    }

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     * idProducto >= 0
     *
     * @param actualizado : producto con los valores actualzados
     * @param idProducto  : id del producto a actualizar
     */
    @Override
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizaProducto(actualizado, idProducto);
    }

    /**
     * elimina un producto
     * pre id >= 0
     *
     * @param idProducto id del producto a eliminar
     */
    @Override
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarProducto(idProducto);
    }

    /**
     * retorna los operarios de la empresa
     *
     * @return operarios de la empresa
     */
    @Override
    public ArrayList<Operario> getOperarios() throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException {
        return state.getOperarios();
    }

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     *
     * @param nuevo operario nuevo
     */
    @Override
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarOperario(nuevo);
    }

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     * idOperario >= 0
     *
     * @param actualizado : operario con los valores actualizados
     * @param idOperario  : id del operario
     */
    @Override
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.actualizarOperario(actualizado, idOperario);
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
    @Override
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException {
        state.cambiarContraseniaOperario(password, newPassword, idOperario);
    }

    /**
     * elimina al operario que corresponde con el id
     * pre : idOperario >= 0
     *
     * @param idOperario : id del operario a eliminar
     */
    @Override
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarOperario(idOperario);
    }

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     * password != null !password.isBlack !password.isEmpty
     *
     * @param userName Nombre de ususario
     * @param password Contraseña
     */
    @Override
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException {
        state.login(userName, password);
    }

    /**
     * Cierra la sesion del ususario actual
     */
    @Override
    public void logout() throws UsuarioNoLogueadoException {
        state.logout();
    }

    /**
     * Abre la empresa
     */
    @Override
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.abrirEmpresa();
    }

    /**
     * Cierra la empresa
     */
    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cerrarEmpresa();
    }

    /**
     * Retorna las asignaciones activas de mozos con mesas
     *
     * @return asignaciones mozo mesa
     */
    @Override
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
    @Override
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.asignaMozo(mozoId, nroMesa, fecha);
    }

    /**
     * Elimina una relacion de mozo con mesa
     *
     * @param nroMesa nro de mesa
     */
    @Override
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarRelacionMozoMeza(nroMesa);
    }

    /**
     * retorna las comandas activas
     *
     * @return comandas activas
     */
    @Override
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException {
        return state.getComandas();
    }

    /**
     * agrega una comanda asignandola a una mesa
     *
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     */
    @Override
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarComanda(nroMesa);
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     *
     * @param nroMesa numero de la mesa
     * @param pago    tipo de  pago
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.cerrarComanda(nroMesa, pago);
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     */
    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarPedido(nroMesa, pedido);
    }

    /**
     * Retorna una lista de todas las promociones
     *
     * @return lista de promociones
     */
    @Override
    public ArrayList<Promocion> getPromociones() throws UsuarioNoLogueadoException {
        return state.getPromociones();
    }

    /**
     * Retorna una lista de las promociones de producto
     *
     * @return lista de promociones de producto
     */
    @Override
    public ArrayList<PromocionProducto> getPromocionesProducto() throws UsuarioNoLogueadoException {
        return state.getPromocionesProducto();
    }

    /**
     * Retorna una lista de las promociones temporales
     *
     * @return lista de promociones temporales
     */
    @Override
    public ArrayList<PromocionTemp> getPromocionesTemporales() throws UsuarioNoLogueadoException {
        return state.getPromocionesTemporales();
    }

    /**
     * Agrega una promocion de producto
     * pre: promo != null
     *
     * @param promo promocion de prodcuto a agregar
     */
    @Override
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarPromocionProducto(promo);
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     *
     * @param promo : promocion temporal a agregar
     */
    @Override
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarPromocionTemp(promo);
    }

    /**
     * Elimina una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a eliminar
     */
    @Override
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.eliminarPromocion(id);
    }

    /**
     * Activa una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a activar
     */
    @Override
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.activarPromocion(id);
    }

    /**
     * Desactiva una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a desactivar
     */
    @Override
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.desactivarPromocion(id);
    }

    /**
     * Retorna una lista con las facturas generadas
     *
     * @return lista de facturas
     */
    @Override
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException {
        return state.getFacturas();
    }

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     *
     * @param factura factura a agregar
     */
    @Override
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException {
        state.agregarFactura(factura);
    }

    /**
     * Retorna las comandas archivadas
     *
     * @return comandas archivadas
     */
    @Override
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException {
        return state.getComandasArchivadas();
    }

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     *
     * @return relaciones archivadas
     */
    @Override
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException {
        return state.getAsignacionMozoMesaArchivadas();
    }

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     *
     * @return coleccion de ventas por mesa
     */
    @Override
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException {
        return state.calculaPromedioPorMesa();
    }

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     *
     * @return coleccion de ventas por mozo
     */
    @Override
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException {
        return state.calculaEstadisticasMozo();
    }

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     *
     * @return informacion de ventas del mozo que mas vendio
     */
    @Override
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMayorVolumenDeVentas();
    }

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     *
     * @return informacion de ventas del mozo que menos vendio
     */
    @Override
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException {
        return state.getMozoConMenorVolumenDeVentas();
    }
}
