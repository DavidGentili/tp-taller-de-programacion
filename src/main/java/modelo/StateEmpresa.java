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

public interface StateEmpresa {

    //CONFIGURACION

    /**
     * Obtiene el nombre del local
     * @return nombre del local;
     */
    public String getNombreLocal() throws UsuarioNoLogueadoException;

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     * @param name nuevo nombre;
     */
    public void cambiarNombreLocal(String name) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException, NoSeCambioContraseniaException;

    /**
     * retorna el elemento sueldo de la empresa
     * @return elemento sueldo
     */
    public Sueldo getSueldo() throws UsuarioNoLogueadoException;

    /**
     * determina el sueldo de la empresa
     * pre: sueldo != null
     * @param sueldo : sueldo de la empresa
     */
    public void setSueldo(Sueldo sueldo) throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna los mozos de la empresa
     * @return mozos de la empresa
     */
    public ArrayList<Mozo> getMozos() throws UsuarioNoLogueadoException;

    /**
     * Agrega un mozo a la empresa
     * pre: nuevo != null
     * @param nuevo nuevo mozo
     */
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Actualiza un mozo
     * pre: actualizado != null
     *      mozoId >= 0
     * @param actualizado : mozo con los valores actualizados
     * @param mozoId : id del mozo a modificar
     */
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     * @param mozoId : id del mozo
     */
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Cambia el estado de un mozo
     * @param mozoId : id del mozo a cambiar
     * @param estado : nuevo estado del mozo
     */
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las mesas de la empresa
     * @return mesas de la empresa
     */
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException;

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     * @param nueva : mesa a agregar
     */
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     *      mesaId >= 0
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa : nro de la mesa a actualizar
     */
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * elimina una mesa con el numero correspondiente
     * pre: mesa >= 0;
     * @param nroMesa : numero de la mesa a liminar
     */
    public void eliminarMesa(int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna los productos de la empresa
     * @return productos de la empresa
     */
    public ArrayList<Producto> getProductos() throws UsuarioNoLogueadoException;

    /**
     * Agrega un producto a la empresa
     * pre : nuevo != null
     * @param nuevo nuevo producto de la empresa
     */
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     *      idProducto >= 0
      * @param actualizado : producto con los valores actualzados
     * @param idProducto : id del producto a actualizar
     */
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * elimina un producto
     * pre id >= 0
     * @param idProducto id del producto a eliminar
     */
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * retorna los operarios de la empresa
     * @return operarios de la empresa
     */
    public ArrayList<Operario> getOperarios() throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException;

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     * @param nuevo operario nuevo
     */
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     *      idOperario >= 0
     * @param actualizado : operario con los valores actualizados
     * @param idOperario : id del operario
     */
    public void actualizarOperario(Operario actualizado, int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Cambia la contraseña de un usuario usuario
     * @param password Contraseña actual
     * @param idOperario id del operario
     * @throws OperarioNoEncontradoException si no se encuentra el operario con dicho id
     * @throws ContraseniaIncorrectaException si la nueva contraseña no cumple con el formato
     * @throws UsuarioNoAutorizadoException  si la actual contraseña no coincide
     */
    public void cambiarContraseniaOperario(String password, String newPassword, int idOperario) throws OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException;


    /**
     * elimina al operario que corresponde con el id
     * pre : idOperario >= 0
     * @param idOperario : id del operario a eliminar
     */
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Inicia sesion de un usuario segun un nombre de usuario y una contraseña
     * pre: userName != null !userName.isBlack !userName.isEmpty
     *      password != null !password.isBlack !password.isEmpty
     * @param userName Nombre de ususario
     * @param password Contraseña
     */
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException;

    /**
     * Cierra la sesion del ususario actual
     */
    public void logout() throws UsuarioNoLogueadoException;

    //GESTOR EMPRESA

    /**
     * Abre la empresa
     */
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Cierra la empresa
     */
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las asignaciones activas de mozos con mesas
     * @return asignaciones mozo mesa
     */
    public ArrayList<MozoMesa> getAsignacionMozoMeza() throws UsuarioNoLogueadoException;

    /**
     * Asigna un mozo a una mesa
     * @param mozoId id del mozo
     * @param nroMesa nro de mesa
     * @param fecha fecha de la asignacion
     */
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina una relacion de mozo con mesa
     * @param nroMesa nro de mesa
     */
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * retorna las comandas activas
     * @return comandas activas
     */
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException;

    /**
     * agrega una comanda asignandola a una mesa
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     */
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     * @param nroMesa numero de la mesa
     * @param pago tipo de  pago
     */
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     * @param nroMesa numero de mesa
     * @param pedido pedido a agregar
     */
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaYaCerradaException, EmpresaCerradaException, ComandaNoEncontradaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna una lista de todas las promociones
     * @return lista de promociones
     */
    public ArrayList<Promocion> getPromociones() throws UsuarioNoLogueadoException;

    /**
     * Retorna una lista de las promociones de producto
     * @return lista de promociones de producto
     */
    public ArrayList<PromocionProducto> getPromocionesProducto() throws UsuarioNoLogueadoException;

    /**
     * Retorna una lista de las promociones temporales
     * @return lista de promociones temporales
     */
    public ArrayList<PromocionTemp> getPromocionesTemporales() throws UsuarioNoLogueadoException;

    /**
     * Agrega una promocion de producto
     * pre: promo != null
     * @param promo promocion de prodcuto a agregar
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * agrega una promocion tempora
     * pre: promo != null
     * @param promo : promocion temporal a agregar
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a eliminar
     */
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Activa una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a activar
     */
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Desactiva una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a desactivar
     */
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    //ARCHIVO

    /**
     * Retorna una lista con las facturas generadas
     * @return lista de facturas
     */
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException;

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     * @param factura factura a agregar
     */
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las comandas archivadas
     * @return comandas archivadas
     */
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException;

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     * @return relaciones archivadas
     */
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException;

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     * @return coleccion de ventas por mesa
     */
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException;

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     * @return coleccion de ventas por mozo
     */
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException;

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     * @return informacion de ventas del mozo que mas vendio
     */
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException;

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     * @return informacion de ventas del mozo que menos vendio
     */
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException;
}
