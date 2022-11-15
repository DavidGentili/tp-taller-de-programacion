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
import modelo.archivo.Factura;
import modelo.archivo.VentasMesa;
import modelo.archivo.VentasMozo;
import modelo.configEmpresa.*;
import modelo.gestorEmpresa.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class LoginState implements StateEmpresa{

    private Empresa empresa;
    private Operario user;

    public LoginState(Empresa empresa){
        this.empresa = empresa;
        this.user = empresa.getUsuario();

    }

    private void cambioContrasenia() throws NoSeCambioContraseniaException {
        if(!empresa.getConfiguracion().isPrimerAcceso())
            throw new NoSeCambioContraseniaException("Debe cambiar la contraseña antes de realizar cualquier accion");
    }

    /**
     * Obtiene el nombre del local
     *
     * @return nombre del local;
     */
    @Override
    public String getNombreLocal() {
        return empresa.getConfiguracion().getNombreLocal();
    }

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     *
     * @param name nuevo nombre;
     * @UsuarioNoAutorizadoException : Si el ususario no esta autorizado
     */
    @Override
    public void cambiarNombreLocal(String name) throws UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert name != null && !name.isBlank() && !name.isEmpty();
        cambioContrasenia();
        empresa.getConfiguracion().cambiaNombreLocal(name, user);
    }

    /**
     * retorna el elemento sueldo de la empresa
     *
     * @return elemento sueldo
     */
    @Override
    public Sueldo getSueldo() {
        return empresa.getConfiguracion().getSueldo();
    }

    /**
     * determina el sueldo de la empresa
     * pre: sueldo != null
     *
     * @param sueldo : sueldo de la empresa
     * @Throw UsuarioNoAutorizadoException: si el usuario no esta autorizado
     */
    @Override
    public void setSueldo(Sueldo sueldo) throws UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert sueldo != null;
        cambioContrasenia();
        empresa.getConfiguracion().setSueldo(sueldo, user);
    }

    /**
     * Retorna los mozos de la empresa
     *
     * @return mozos de la empresa
     */
    @Override
    public ArrayList<Mozo> getMozos() {
        return empresa.getConfiguracion().getMozos();
    }

    /**
     * Agrega un mozo a la empresa
     * pre: nuevo != null
     *
     * @param nuevo nuevo mozo
     */
    @Override
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().agregaMozo(nuevo,user);
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
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().actualizarMozo(actualizado, mozoId, user);
    }

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     *
     * @param mozoId : id del mozo
     */
    @Override
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().eliminaMozo(mozoId, user);
    }

    /**
     * Cambia el estado de un mozo
     *
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     */
    @Override
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().cambiarEstadoMozo(mozoId, estado);
    }

    /**
     * Retorna las mesas de la empresa
     *
     * @return mesas de la empresa
     */
    @Override
    public ArrayList<Mesa> getMesas() {
        return empresa.getConfiguracion().getMesas();
    }

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     *
     * @param nueva : mesa a agregar
     */
    @Override
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().agregarMesa(nueva, user);
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
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().actulizarMesa(actualizada, nroMesa, user);
    }

    /**
     * elimina una mesa con el numero correspondiente
     * pre: mesa >= 0;
     *
     * @param nroMesa : numero de la mesa a liminar
     */
    @Override
    public void eliminarMesa(int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().eliminarMesa(nroMesa, user);
    }

    /**
     * Retorna los productos de la empresa
     *
     * @return productos de la empresa
     */
    @Override
    public ArrayList<Producto> getProductos() {
        return empresa.getConfiguracion().getProductos();
    }

    /**
     * Agrega un producto a la empresa
     * pre : nuevo != null
     *
     * @param nuevo nuevo producto de la empresa
     */
    @Override
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().agregarProducto(nuevo, user);
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
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().actulizarProducto(actualizado, idProducto, user);
    }

    /**
     * elimina un producto
     * pre id >= 0
     *
     * @param idProducto id del producto a eliminar
     */
    @Override
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().eliminarProducto(idProducto, user);
    }

    /**
     * retorna los operarios de la empresa
     *
     * @return operarios de la empresa
     */
    @Override
    public ArrayList<Operario> getOperarios() throws UsuarioNoAutorizadoException {
        return empresa.getConfiguracion().getOperarios(user);
    }

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     *
     * @param nuevo operario nuevo
     */
    @Override
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().agregarOperario(nuevo, user);
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
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().actualizarOperario(actualizado, idOperario, user);
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
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException {
        assert password != null;
        assert newPassword != null;
        if(empresa.getUsuario().getId() != idOperario)
            throw new UsuarioNoAutorizadoException("El usuario actual no se encuentra autorizado a ");
        empresa.getConfiguracion().cambiarContraseniaOperario(password, newPassword, idOperario);
    }

    /**
     * elimina al operario que corresponde con el id
     * pre : idOperario >= 0
     *
     * @param idOperario : id del operario a eliminar
     */
    @Override
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getConfiguracion().eliminarOperario(idOperario, user);
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
    public void login(String userName, String password) throws UsuarioYaLogueadoException {
        throw new UsuarioYaLogueadoException("El usuario ya esta logueado");
    }

    /**
     * Cierra la sesion del ususario actual
     */
    @Override
    public void logout(){
        empresa.setUsuario(null);
        empresa.setState(new LogoutState(empresa));
    }

    /**
     * Abre la empresa
     */
    @Override
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().abrirEmpresa();
    }

    /**
     * Cierra la empresa
     */
    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().cerrarEmpresa();
    }

    /**
     * Retorna las asignaciones activas de mozos con mesas
     *
     * @return asignaciones mozo mesa
     */
    @Override
    public ArrayList<MozoMesa> getAsignacionMozoMeza() {
        return empresa.getGestorEmpresa().getAsignacionMozosMesas();
    }

    /**
     * Asigna un mozo a una mesa
     *
     * @param mozoId  id del mozo
     * @param nroMesa nro de mesa
     * @param fecha   fecha de la asignacion
     */
    @Override
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().asignarMozo(mozoId, nroMesa, fecha);
    }

    /**
     * Elimina una relacion de mozo con mesa
     *
     * @param nroMesa nro de mesa
     */
    @Override
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().eliminarRelacionMozoMesa(nroMesa);
    }

    /**
     * retorna las comandas activas
     *
     * @return comandas activas
     */
    @Override
    public ArrayList<Comanda> getComandas() {
        return empresa.getGestorEmpresa().getComandas();
    }

    /**
     * agrega una comanda asignandola a una mesa
     *
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     */
    @Override
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarComanda(nroMesa);
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     *
     * @param nroMesa numero de la mesa
     * @param pago    tipo de  pago
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().cerrarComanda(nroMesa, pago);
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     */
    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarPedido(nroMesa, pedido);
    }

    /**
     * Retorna una lista de todas las promociones
     *
     * @return lista de promociones
     */
    @Override
    public ArrayList<Promocion> getPromociones() {
        return empresa.getGestorEmpresa().getPromociones();
    }

    /**
     * Retorna una lista de las promociones de producto
     *
     * @return lista de promociones de producto
     */
    @Override
    public ArrayList<PromocionProducto> getPromocionesProducto() {
        return empresa.getGestorEmpresa().getPromocionesProducto();
    }

    /**
     * Retorna una lista de las promociones temporales
     *
     * @return lista de promociones temporales
     */
    @Override
    public ArrayList<PromocionTemp> getPromocionesTemporales() {
        return empresa.getGestorEmpresa().getPromocionesTemporales();
    }

    /**
     * Agrega una promocion de producto
     * pre: promo != null
     *
     * @param promo promocion de prodcuto a agregar
     */
    @Override
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarPromocionProducto(promo);
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     *
     * @param promo : promocion temporal a agregar
     */
    @Override
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarPromocionTemp(promo);
    }

    /**
     * Elimina una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a eliminar
     */
    @Override
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().eliminarPromocion(id);
    }

    /**
     * Activa una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a activar
     */
    @Override
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().activarPromocion(id);
    }

    /**
     * Desactiva una promocion segun un id
     * pre: id >= 0
     *
     * @param id : id de la promocion a desactivar
     */
    @Override
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().desactivarpromocion(id);
    }

    /**
     * Retorna una lista con las facturas generadas
     *
     * @return lista de facturas
     */
    @Override
    public ArrayList<Factura> getFacturas() {
        return empresa.getArchivo().getFacturas();
    }

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     *
     * @param factura factura a agregar
     */
    @Override
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getArchivo().agregaFacturas(factura);
    }

    /**
     * Retorna las comandas archivadas
     *
     * @return comandas archivadas
     */
    @Override
    public ArrayList<Comanda> getComandasArchivadas() {
        return empresa.getArchivo().getComandas();
    }

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     *
     * @return relaciones archivadas
     */
    @Override
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() {
        return empresa.getArchivo().getAsignacionesMozoMesa();
    }

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     *
     * @return coleccion de ventas por mesa
     */
    @Override
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() {
        return empresa.getArchivo().consumoPromedioXMesa();
    }

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     *
     * @return coleccion de ventas por mozo
     */
    @Override
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() {
        return empresa.getArchivo().calculaEstadisticasMozo();
    }

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     *
     * @return informacion de ventas del mozo que mas vendio
     */
    @Override
    public VentasMozo getMozoConMayorVolumenDeVentas() {
        return empresa.getArchivo().getMozoConMayorVolumenDeVentas();
    }

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     *
     * @return informacion de ventas del mozo que menos vendio
     */
    @Override
    public VentasMozo getMozoConMenorVolumenDeVentas() {
        return empresa.getArchivo().getMozoConMenorVolumenDeVentas();
    }
}
