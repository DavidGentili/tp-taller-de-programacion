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

/**
 * Implementacion de estado de la empresa cuando el usuario esta logueado
 */
public class LoginState implements StateEmpresa{

    private Empresa empresa;
    private Operario user;

    public LoginState(Empresa empresa){
        this.empresa = empresa;
        this.user = empresa.getUsuario();

    }

    /**
     * Retorna si el usuario de la sesion cambio la contraseña
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña del usuario de la sesion
     */
    private void cambioContrasenia() throws NoSeCambioContraseniaException {
        if(empresa.requiereCambioContraseña())
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
     * @param name nuevo nombre;
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     **/
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
     * @throws UsuarioNoAutorizadoException: si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MozoYaAgregadoException Si el mozo a agregar ya existe
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, NoSeCambioContraseniaException {
        assert nuevo != null : "El mozo no puede ser nulo";
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
     * @throws MozoNoEncontradoException Si no se encontro el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El mozo no puede ser nulo";
        cambioContrasenia();
        empresa.getConfiguracion().actualizarMozo(actualizado, mozoId, user);
    }

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     *
     * @param mozoId : id del mozo
     * @throws MozoNoEncontradoException Si no se encontro el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws MozoNoEncontradoException Si no se encuentra el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws MesaYaExistenteException Si la mesa ya existe
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si el usuario no esta autorizado
     */
    @Override
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert nueva != null : "La mesa no puede ser nula";
        cambioContrasenia();
        empresa.getConfiguracion().agregarMesa(nueva, user);
    }

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     *
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa     : nro de la mesa a actualizar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert actualizada != null : "La mesa no puede ser nula";
        cambioContrasenia();
        empresa.getConfiguracion().actulizarMesa(actualizada, nroMesa, user);
    }

    /**
     * elimina una mesa con el numero correspondiente
     *
     * @param nroMesa : numero de la mesa a liminar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws ProductoYaExistenteException Si el producto ya existe
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert nuevo != null : "El producto no puede ser nulo";
        cambioContrasenia();
        empresa.getConfiguracion().agregarProducto(nuevo, user);
    }

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     *
     * @param actualizado : producto con los valores actualzados
     * @param idProducto  : id del producto a actualizar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El producto no puede ser nulo";
        cambioContrasenia();
        empresa.getConfiguracion().actulizarProducto(actualizado, idProducto, user);
    }

    /**
     * elimina un producto
     *
     * @param idProducto id del producto a eliminar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
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
     * @throws OperarioYaExistenteException Si el operario ya existe
     * @throws UsuarioNoAutorizadoException si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert nuevo != null : "El operario no puede ser nulo";
        cambioContrasenia();
        empresa.getConfiguracion().agregarOperario(nuevo, user);
    }

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     *
     * @param actualizado : operario con los valores actualizados
     * @param idOperario  : id del operario
     * @throws OperarioNoEncontradoException Si no se encuentra el operario
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException si el ususario no esta autorizado
     * @throws NoSeCambioContraseniaException si no se cambio la contraseña
     */
    @Override
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException {
        assert actualizado != null : "El operario no puede ser nulo";
        cambioContrasenia();
        empresa.getConfiguracion().actualizarOperario(actualizado, idOperario, user);
    }

    /**
     * Cambia la contraseña de un usuario usuario
     *
     * @param password    Contraseña actual
     * @param newPassword Contraseña nueva
     * @param idOperario  id del operario
     * @throws OperarioNoEncontradoException  si no se encuentra el operario con dicho id
     * @throws ContraseniaIncorrectaException si la nueva contraseña no cumple con el formato
     * @throws UsuarioNoAutorizadoException   si la actual contraseña no coincide
     * @throws IdIncorrectoException Si el id del operario es incorrecto
     */
    @Override
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, IdIncorrectoException {
        assert password != null : "La contraseña no puede ser nula";
        assert newPassword != null : "la contraseña no puede ser nula";
        if(empresa.getUsuario().getId() != idOperario)
            throw new UsuarioNoAutorizadoException("El usuario actual no se encuentra autorizado a ");
        empresa.getConfiguracion().cambiarContraseniaOperario(password, newPassword, idOperario);
    }

    /**
     * elimina al operario que corresponde con el id
     *
     * @param idOperario : id del operario a eliminar
     * @throws OperarioNoEncontradoException Si no se encuentra operario
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws EliminarOperarioLogueadoException Si se quiere eliminar el usuario logueado
     */
    @Override
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException, EliminarOperarioLogueadoException {
        cambioContrasenia();
        if(idOperario == getIdUsuario())
            throw new EliminarOperarioLogueadoException("No se puede eliminar al operario logueado");
        empresa.getConfiguracion().eliminarOperario(idOperario, user);
    }

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     * password != null !password.isBlack !password.isEmpty
     *
     * @param userName Nombre de ususario
     * @param password Contraseña
     * @throws UsuarioYaLogueadoException Si ya se encuentra logueado un usuario
     */
    @Override
    public void login(String userName, String password) throws UsuarioYaLogueadoException {
        assert userName != null && !userName.isBlank() && !userName.isEmpty() : "El nombre de usuario debe ser distinto de nulo y vacio";
        assert password != null && !password.isBlank() && !password.isEmpty() : "La contraseña debe ser distinto de nulo y vacio";

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
     * @throws NoHayMozosAsignadosException Si no hay mozos asignados
     * @throws CantidadMinimaDeProductosEnPromocionException Si no se cumple con la cantidad de productos minimos en promocion
     * @throws CantidadMaximaDeMozosActivosException Si se supera la cantidad maxima de mosos activos
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws CantidadMinimaDeProductosException Si no se supera la cantidad minima de productos
     * @throws CantidadMaximaDeMozosSuperadaException Si se supera la cantidad maxima de mozos
     * @throws CantidadMaximaDeMozosDeFrancoException Si se supera la cantidad maxima de mozos de franco
     * @throws HayMozoSinEstadoAsignadoException Si hay mozos con estados sin asignar
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().abrirEmpresa();
    }

    /**
     * Cierra la empresa
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws HayComandasActivasException Si hay comandas abiertas
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * pre: idMozo >= 0
     * nroMesa >= 0
     *
     * @param mozoId  id del mozo
     * @param nroMesa nro de mesa
     * @param fecha   fecha de la asignacion
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws MozoNoEncontradoException Si no se encuentra el mozo
     * @throws MozoNoActivoException Si el mozo no esta activo
     * @throws EmpresaAbiertaException Si la empresa esta abierta
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, NoSeCambioContraseniaException {
        assert mozoId >= 0 : "El id del mozo no puede ser negativo";
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        cambioContrasenia();
        empresa.getGestorEmpresa().asignarMozo(mozoId, nroMesa, fecha);
    }

    /**
     * Elimina una relacion de mozo con mesa
     *
     * @param nroMesa nro de mesa
     * @throws MesaNoAsignadaException Si la mesa no esta asignada
     * @throws EmpresaAbiertaException Si la empresa no esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * pre : nroMesa >= 0
     *
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     * @throws EmpresaCerradaException Si la empresa ya esta cerrada
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws MesaNoEncontradaException Si no se encontro la mesa
     */
    @Override
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, NoSeCambioContraseniaException, MesaNoEncontradaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarComanda(nroMesa);
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     * pre: nroMesa >= 0
     *      pago != null
     *
     * @param nroMesa numero de la mesa
     * @param pago    tipo de  pago
     * @throws ComandaYaCerradaException Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws MesaNoEncontradaException si no se encontro la mesa
     * @throws MesaYaLiberadaException Si la mesa ya esta liberada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, NoSeCambioContraseniaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        assert pago != null : "El metodo de pago no puede ser nulo";
        cambioContrasenia();
        empresa.getGestorEmpresa().cerrarComanda(nroMesa, pago);
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a ingresar
     * @throws ComandaYaCerradaException Si la comanda ya se encuentra cerrada
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws ComandaNoEncontradaException Si no se encuentra la comanda
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, NoSeCambioContraseniaException {
        assert pedido != null : "El pedido no puede ser nulo";
        assert nroMesa >= 0 : "El nro de meso no puede ser nulo";
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
     * @throws PromocionYaExistenteException Si la promocion ya existe
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, NoSeCambioContraseniaException {
        assert promo != null : "La promocion no puede ser nula";
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarPromocionProducto(promo);
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     *
     * @param promo : promocion temporal a agregar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, NoSeCambioContraseniaException {
        assert promo != null : "La promocion no puede ser nula";
        cambioContrasenia();
        empresa.getGestorEmpresa().agregarPromocionTemp(promo);
    }

    /**
     * Elimina una promocion segun un id
     *
     * @param id : id de la promocion a eliminar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().eliminarPromocion(id);
    }

    /**
     * Activa una promocion segun un id
     *
     * @param id : id de la promocion a activar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    @Override
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, NoSeCambioContraseniaException {
        cambioContrasenia();
        empresa.getGestorEmpresa().activarPromocion(id);
    }

    /**
     * Desactiva una promocion segun un id
     *
     * @param id : id de la promocion a desactivar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
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
     * @throws FacturaYaExistenteException Si la factura que se quiere agregar ya existe
     * @throws NoSeCambioContraseniaException Si todavia no se cambia la contraseña
     */
    @Override
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, NoSeCambioContraseniaException {
        assert factura != null : "La factura no puede ser nula";
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

    /**
     * Retorna el id del usuario logueado
     * @return id del usuario logueado
     */
    @Override
    public int getIdUsuario(){
        return empresa.getUsuario().getId();
    }
}
