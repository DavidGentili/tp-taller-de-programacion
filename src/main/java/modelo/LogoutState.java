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
 * Implementacion de estado de la empresa cuando el usuario no esta logueado
 */
public class LogoutState implements StateEmpresa{

    private Empresa empresa;

    public LogoutState(Empresa empresa){
        this.empresa = empresa;
    }


    /**
     * Obtiene el nombre del local
     *
     * @return nombre del local;
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public String getNombreLocal() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     *
     * @param name nuevo nombre;
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void cambiarNombreLocal(String name) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * retorna el elemento sueldo de la empresa
     *
     * @return elemento sueldo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public Sueldo getSueldo() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * determina el sueldo de la empresa
     * pre: sueldo != null
     *
     * @param sueldo : sueldo de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void setSueldo(Sueldo sueldo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna los mozos de la empresa
     *
     * @return mozos de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Mozo> getMozos() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Agrega un mozo a la empresa
     * pre: nuevo != null
     *
     * @param nuevo nuevo mozo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarMozo(Mozo nuevo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Actualiza un mozo
     * pre: actualizado != null
     *
     * @param actualizado : mozo con los valores actualizados
     * @param mozoId      : id del mozo a modificar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void actualizarMozo(Mozo actualizado, int mozoId) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Elimina el mozo correspondiente a la id
     *
     * @param mozoId : id del mozo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarMozo(int mozoId) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Cambia el estado de un mozo
     *
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna las mesas de la empresa
     *
     * @return mesas de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     *
     * @param nueva : mesa a agregar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarMesa(Mesa nueva) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     *
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa     : nro de la mesa a actualizar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * elimina una mesa con el numero correspondiente
     *
     * @param nroMesa : numero de la mesa a liminar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarMesa(int nroMesa) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna los productos de la empresa
     *
     * @return productos de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Producto> getProductos() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Agrega un producto a la empresa
     * pre : nuevo != null
     *
     * @param nuevo nuevo producto de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarProducto(Producto nuevo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     *
     * @param actualizado : producto con los valores actualzados
     * @param idProducto  : id del producto a actualizar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void actualizaProducto(Producto actualizado, int idProducto) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * elimina un producto
     *
     * @param idProducto id del producto a eliminar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarProducto(int idProducto) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * retorna los operarios de la empresa
     *
     * @return operarios de la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Operario> getOperarios() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     *
     * @param nuevo operario nuevo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarOperario(Operario nuevo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     *
     * @param actualizado : operario con los valores actualizados
     * @param idOperario  : id del operario
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void actualizarOperario(Operario actualizado, int idOperario) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Cambia la contraseña de un usuario usuario
     * @param password Contraseña actual
     * @param newPassword  Nueva contraseña
     * @param idOperario  id del operario
     * @throws UsuarioNoLogueadoException : Si el usuario no esta logueado
     */
    @Override
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * elimina al operario que corresponde con el id
     *
     * @param idOperario : id del operario a eliminar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarOperario(int idOperario) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     * password != null !password.isBlack !password.isEmpty
     *
     * @param userName Nombre de ususario
     * @param password Contraseña
     * @throws UsuarioYaLogueadoException : Si ya existe un usuario logueado
     * @throws OperarioInactivoException : Si el operario que se quiere iniciar sesion se encuentra inactivo
     * @throws DatosLoginIncorrectosException : Si los datos para el login son incorrectos (nombre de usuario o contraseña)
     */
    @Override
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException {
        Operario user = empresa.getConfiguracion().login(userName, password);
        empresa.setUsuario(user);
        empresa.setState(new LoginState(empresa));
    }

    /**
     * Cierra la sesion del ususario actual
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void logout() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Abre la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void abrirEmpresa() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Cierra la empresa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void cerrarEmpresa() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna las asignaciones activas de mozos con mesas
     *
     * @return asignaciones mozo mesa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<MozoMesa> getAsignacionMozoMeza() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Asigna un mozo a una mesa
     * pre: idMozo >= 0
     * nroMesa >= 0
     *
     * @param mozoId  id del mozo
     * @param nroMesa nro de mesa
     * @param fecha   fecha de la asignacion
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Elimina una relacion de mozo con mesa
     *
     * @param nroMesa nro de mesa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * retorna las comandas activas
     *
     * @return comandas activas
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * agrega una comanda asignandola a una mes
     * pre : nroMesa >= 0
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarComanda(int nroMesa) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     * pre : nroMesa >= 0
     *      pago != null
     *
     * @param nroMesa numero de la mesa
     * @param pago    tipo de  pago
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     * pre: nroMesa >= 0
     *      pedido != null
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una lista de todas las promociones
     *
     * @return lista de promociones
     */
    @Override
    public ArrayList<Promocion> getPromociones() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una lista de las promociones de producto
     *
     * @return lista de promociones de producto
     */
    @Override
    public ArrayList<PromocionProducto> getPromocionesProducto() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una lista de las promociones temporales
     *
     * @return lista de promociones temporales
     */
    @Override
    public ArrayList<PromocionTemp> getPromocionesTemporales() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Agrega una promocion de producto
     * pre: promo != null
     *
     * @param promo promocion de prodcuto a agregar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarPromocionProducto(PromocionProducto promo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * agrega una promocion tempora
     * pre: promo != null
     *
     * @param promo : promocion temporal a agregar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarPromocionTemp(PromocionTemp promo) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Elimina una promocion segun un id
     *
     * @param id : id de la promocion a eliminar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void eliminarPromocion(int id) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Activa una promocion segun un id
     *
     * @param id : id de la promocion a activar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void activarPromocion(int id) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Desactiva una promocion segun un id
     *
     * @param id : id de la promocion a desactivar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void desactivarPromocion(int id) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una lista con las facturas generadas
     *
     * @return lista de facturas
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     * @param factura factura a agregar
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public void agregarFactura(Factura factura) throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna las comandas archivadas
     *
     * @return comandas archivadas
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     *
     * @return relaciones archivadas
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     *
     * @return coleccion de ventas por mesa
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     *
     * @return coleccion de ventas por mozo
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     *
     * @return informacion de ventas del mozo que mas vendio
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     *
     * @return informacion de ventas del mozo que menos vendio
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }

    /**
     * Retorna el id del usuario logueado
     * @return id del usuario logueado
     * @throws UsuarioNoLogueadoException : si el usuarion no esta logueado
     */
    @Override
    public int getIdUsuario() throws UsuarioNoLogueadoException {
        throw new UsuarioNoLogueadoException("El usuario no esta logueado");
    }
}
