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
 * Interfacaz que debe cumplir un estado de la empresa
 */
public interface StateEmpresa {

    //CONFIGURACION

    /**
     * Obtiene el nombre del local
     * @return nombre del local;
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public String getNombreLocal() throws UsuarioNoLogueadoException;

    /**
     * cambia el nombre del local
     * pre : name != null !name.isBlank() !name.isEmpty;
     * @param name nuevo nombre;
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
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
     * @throws UsuarioNoAutorizadoException: si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
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
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MozoYaAgregadoException Si el mozo a agregar ya existe
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarMozo(Mozo nuevo) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

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
    public void actualizarMozo(Mozo actualizado, int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina el mozo correspondiente a la id
     * pre: mozoId >= 0;
     * @param mozoId : id del mozo
     * @throws MozoNoEncontradoException Si no se encontro el mozo
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws EmpresaAbiertaException si la empresa esta abierta
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarMozo(int mozoId) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

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
    public void cambiarEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las mesas de la empresa
     * @return mesas de la empresa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Mesa> getMesas() throws UsuarioNoLogueadoException;

    /**
     * Agrega una mesa a la empresa
     * pre : nueva != null
     * @param nueva : mesa a agregar
     * @throws MesaYaExistenteException Si la mesa ya existe
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si el usuario no esta autorizado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarMesa(Mesa nueva) throws MesaYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Actualiza una mesa
     * pre: actualizada != null
     *      mesaId >= 0
     * @param actualizada : mesa con valores nuevos
     * @param nroMesa : nro de la mesa a actualizar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void actualizarMesa(Mesa actualizada, int nroMesa) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * elimina una mesa con el numero correspondiente
     * pre: mesa >= 0;
     * @param nroMesa : numero de la mesa a liminar
     * @throws MesaNoEncontradaException Si no se encuentra la mesa
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
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
     * @throws ProductoYaExistenteException Si el producto ya existe
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarProducto(Producto nuevo) throws ProductoYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * actualiza el producto con el id correspondiente
     * pre: actualizado != null
     *      idProducto >= 0
      * @param actualizado : producto con los valores actualzados
     * @param idProducto : id del producto a actualizar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void actualizaProducto(Producto actualizado, int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * elimina un producto
     * pre id >= 0
     * @param idProducto id del producto a eliminar
     * @throws ProductoNoEncontradoException Si no se encontro el producto
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException Si el usuario no esta autorizado
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarProducto(int idProducto) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * retorna los operarios de la empresa
     * @return operarios de la empresa
     * @throws UsuarioNoAutorizadoException si el usuario no esta autorizado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Operario> getOperarios() throws UsuarioNoAutorizadoException, UsuarioNoLogueadoException;

    /**
     * agrega un nuevo operario
     * pre : nuevo != null
     * @param nuevo operario nuevo
     * @throws OperarioYaExistenteException Si el operario ya existe
     * @throws UsuarioNoAutorizadoException si el usuario no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void agregarOperario(Operario nuevo) throws OperarioYaExistenteException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * actualiza un operario, correspondiente con el id
     * pre: actualizado != null
     *      idOperario >= 0
     * @param actualizado : operario con los valores actualizados
     * @param idOperario : id del operario
     * @throws OperarioNoEncontradoException Si no se encuentra el operario
     * @throws IdIncorrectoException Si el id es incorrecto
     * @throws UsuarioNoAutorizadoException si el ususario no esta autorizado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException si no se cambio la contraseña
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
     * @throws OperarioNoEncontradoException Si no se encuentra operario
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoAutorizadoException Si el usuarion no esta autorizado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws EliminarOperarioLogueadoException Si se quiere eliminar el usuario logueado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void eliminarOperario(int idOperario) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, EliminarOperarioLogueadoException;

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
    public void login(String userName, String password) throws UsuarioYaLogueadoException, OperarioInactivoException, DatosLoginIncorrectosException;

    /**
     * Cierra la sesion del ususario actual
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public void logout() throws UsuarioNoLogueadoException;

    //GESTOR EMPRESA

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
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Cierra la empresa
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws HayComandasActivasException Si hay comandas abiertas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las asignaciones activas de mozos con mesas
     * @return asignaciones mozo mesa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<MozoMesa> getAsignacionMozoMeza() throws UsuarioNoLogueadoException;

    /**
     * Asigna un mozo a una mesa
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
    public void asignaMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina una relacion de mozo con mesa
     * @param nroMesa nro de mesa
     * @throws MesaNoAsignadaException Si la mesa no esta asignada
     * @throws EmpresaAbiertaException Si la empresa no esta abierta
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void eliminarRelacionMozoMeza(int nroMesa) throws MesaNoAsignadaException, EmpresaAbiertaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * retorna las comandas activas
     * @return comandas activas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Comanda> getComandas() throws UsuarioNoLogueadoException;

    /**
     * agrega una comanda asignandola a una mesa
     * @param nroMesa nro de la mesa a la cual se asigna la comanda
     * @throws EmpresaCerradaException Si la empresa ya esta cerrada
     * @throws MesaYaOcupadaException Si la mesa ya esta ocupada
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     * @throws MesaNoEncontradaException si no se encontro la mesa
     */
    public void agregarComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException, MesaNoEncontradaException;

    /**
     * Cierra una comanda segun un tipo de pago y un numero de mesa
     * @param nroMesa numero de la mesa
     * @param pago tipo de  pago
     * @throws ComandaYaCerradaException Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException Si la empresa esta cerrada
     * @throws MesaNoEncontradaException si no se encontro la mesa
     * @throws MesaYaLiberadaException Si la mesa ya esta liberada
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void cerrarComanda(int nroMesa, FormasDePago pago) throws ComandaYaCerradaException, EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * agrega un pedido a una comanda, segun un numero de mesa
     *
     * @param nroMesa numero de mesa
     * @param pedido  pedido a agregar
     * @throws ComandaYaCerradaException      : Si la comanda ya esta cerrada
     * @throws EmpresaCerradaException        : Si la empresa esta cerrada
     * @throws ComandaNoEncontradaException   : SI no se encontro la comanda
     * @throws UsuarioNoLogueadoException     : si el ususario no esta logueado
     * @throws NoSeCambioContraseniaException : Si no se cambio la contraseña
     * @throws ProductoNoEncontradoException  : Si el producto nose encontro
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
     * @throws PromocionYaExistenteException Si la promocion ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * agrega una promocion tempora
     * pre: promo != null
     * @param promo : promocion temporal a agregar
     * @throws PromocionYaExistenteException Si la promocion ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Elimina una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a eliminar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void eliminarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Activa una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a activar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void activarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Desactiva una promocion segun un id
     * pre: id >= 0
     * @param id : id de la promocion a desactivar
     * @throws PromocionNoEncontradaException Si no se encontro la promocion
     * @throws IdIncorrectoException Si el id no es correcto
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si no se cambio la contraseña
     */
    public void desactivarPromocion(int id) throws PromocionNoEncontradaException, IdIncorrectoException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    //ARCHIVO

    /**
     * Retorna una lista con las facturas generadas
     * @return lista de facturas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Factura> getFacturas() throws UsuarioNoLogueadoException;

    /**
     * Agrega una factura al archivo
     * pre : factura != null
     * @param factura factura a agregar
     * @throws FacturaYaExistenteException Si la factura que se quiere agregar ya existe
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     * @throws NoSeCambioContraseniaException Si todavia no se cambia la contraseña
     */
    public void agregarFactura(Factura factura) throws FacturaYaExistenteException, UsuarioNoLogueadoException, NoSeCambioContraseniaException;

    /**
     * Retorna las comandas archivadas
     * @return comandas archivadas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<Comanda> getComandasArchivadas() throws UsuarioNoLogueadoException;

    /**
     * Retorna las relaciones mozo mesa ya archivadas
     * @return relaciones archivadas
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public ArrayList<MozoMesa> getAsignacionMozoMesaArchivadas() throws UsuarioNoLogueadoException;

    /**
     * Retorna una coleccion de ventas por mesa, con total y promedio
     * @return coleccion de ventas por mesa
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public HashMap<Integer, VentasMesa> calculaPromedioPorMesa() throws UsuarioNoLogueadoException;

    /**
     * Retorna una coleccion de ventas por mozo, con total y promedio
     * @return coleccion de ventas por mozo
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public HashMap<Integer, VentasMozo> calculaEstadisticasMozo() throws UsuarioNoLogueadoException;

    /**
     * Retorna la informacion de ventas del mozo que mas vendio
     * @return informacion de ventas del mozo que mas vendio
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public VentasMozo getMozoConMayorVolumenDeVentas() throws UsuarioNoLogueadoException;

    /**
     * Retorna la informacion de ventas del mozo que menos vendio
     * @return informacion de ventas del mozo que menos vendio
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public VentasMozo getMozoConMenorVolumenDeVentas() throws UsuarioNoLogueadoException;

    /**
     * Retorna el id del usuario logueado
     * @return id del usuario logueado
     * @throws UsuarioNoLogueadoException si el usuarion no esta logueado
     */
    public int getIdUsuario() throws UsuarioNoLogueadoException;
}
