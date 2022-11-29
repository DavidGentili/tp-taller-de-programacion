package modelo.gestorEmpresa;

import enums.EstadoMozos;
import enums.FormasDePago;
import exceptions.*;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.MesaNoAsignadaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaLiberadaException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.GregorianCalendar;

/**
 * Interfaz que representa el estado del gestor de la empresa
 */
public interface StateGestorEmpresa {

    //METODOS CAMBIO DE ESTADO

    /**
     * Abre la empresa
     * @throws NoHayMozosAsignadosException : No hay mozos asignados
     * @throws CantidadMinimaDeProductosException : No se cumple la cantidad minima de productos
     * @throws CantidadMinimaDeProductosEnPromocionException : No se cumple la cantidad minima de productos en promocion
     * @throws CantidadMaximaDeMozosSuperadaException : Se supero la cantidad maxima de mozos
     * @throws CantidadMaximaDeMozosActivosException : Se supero la cantida maxima de mozos activo
     * @throws EmpresaAbiertaException : La empresa ya esta abierta
     * @throws CantidadMaximaDeMozosDeFrancoException : Se supero la cantidad maxima de mozos de franco
     * @throws HayMozoSinEstadoAsignadoException : Hay mozos sin estado asignado
     */
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException;

    /**
     * Se cierra la empresa, se limpian los estados de los mozos y se vacian las asignaciones
     * @throws EmpresaCerradaException Si la empresa ya esta cerrada
     * @throws HayComandasActivasException Si todavia existen comandas activas
     */
    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException;

    //METODOS ASIGNACION MOZO MEZA

    /**
     * Asigna un mozo a una mesa
     * pre: idMozo >= 0
     * nroMesa >= 0
     * @param idMozo : id del mozo
     * @param nroMesa : numero de la mesa
     * @param fecha : fecha de la asignacion
     * @throws MozoNoActivoException : El mozo asignado no esta activo
     * @throws MesaYaOcupadaException : La mesa ya esta ocupada
     * @throws MozoNoEncontradoException : No se encontro el mozo con ese id
     * @throws MesaNoEncontradaException : No se encontro mesa con ese numero de mesa
     * @throws EmpresaAbiertaException : La empresa ya esta abierta
     */
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws MozoNoActivoException, MesaYaOcupadaException, MozoNoEncontradoException, MesaNoEncontradaException, EmpresaAbiertaException;

    /**
     * Elimina una relacion mozo con mesa
     * pre: nroMesa >= 0
     * @param nroMesa : Numero de mesa a la cual desasignar
     * @throws EmpresaAbiertaException : Si la empresa se encuentra abierta
     * @throws MesaNoAsignadaException : Si no existe dicha asignacion
     */
    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException, MesaNoAsignadaException;

    //METODOS COMANDAS

    /**
     * Agrega una nueva comanda al sistema
     * pre: nroMesa >= 0
     * @param nroMesa Mesa a la cual asignar la nueva comanda
     * @throws EmpresaCerradaException La empresa ya se encuentra cerrada
     * @throws MesaYaOcupadaException La mesa ya se encuentra ocupada
     */
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException, MesaNoEncontradaException;

    /**
     * Se cierra una comanda, se crea la factura y se la almacena en archivo
     * pre: nroMesa >= 0
     * @param nroMesa Numero de la mesa a la cual cerrar la comanda
     * @param formaDePago : La forma de pago
     * @throws EmpresaCerradaException : La empresa ya se encuentra cerrada
     * @throws MesaNoEncontradaException : Si no se encontro la mesa
     * @throws MesaYaLiberadaException : Si la mesa ya se encuentra liberada
     * @throws ComandaYaCerradaException : Si la comanda que se desea cerrar ya se encuentra cerrada
     */
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, ComandaYaCerradaException;

    /**
     * Agrega un pedido a una comanda especifica
     * pre: nroMesa >= 0
     *      pedido != null
     * @param nroMesa numero de mesa a la cual adicionar el pedido
     * @param pedido : pedido a agregar
     * @throws EmpresaCerradaException : Si la empresa se encuentra cerrada
     * @throws ComandaNoEncontradaException : Si no se encuentra la comanda
     * @throws ComandaYaCerradaException : Si la comanda ya se encuentra cerrda
     */
    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException, ComandaNoEncontradaException, ComandaYaCerradaException;


    //METODOS MOZOS

    /**
     * Retorna si el sistema puede agregar un mozo
     * @return puede agregar un mozo
     * @throws EmpresaAbiertaException : Si la empresa se encuentra abierta
     */
    public boolean puedeAgregarMozo() throws EmpresaAbiertaException;

    /**
     * Retorna si el sistema puede definir el estado de un mozo
     * @return : puede definir el estado de unmozo
     * @throws EmpresaAbiertaException Si la empresa ya se encuentra abierta
     */
    public boolean puedeDefinirEstadoMozo() throws EmpresaAbiertaException;

    /**
     * Retorna si el sistema puede eliminar un mozo
     * @param idMozo id del mozo que se quiere eliminar
     * @return : Sis se puede eliminar el mozo
     * @throws MozoNoEncontradoException : No se encuentra el mozo correspondiente a ese id
     * @throws EmpresaAbiertaException : La empresa se encuentra abierta
     */
    public boolean puedeEliminarMozo(int idMozo) throws MozoNoEncontradoException, EmpresaAbiertaException;

    //METODOS MESAS

    /**
     * Retorna si el sistema puede eliminar una mesa
     * pre: nroMesa >= 0
     * @param nroMesa numero de la mesa
     * @return Retorna si la mesa puede ser eliminada
     * @throws MesaNoEncontradaException : Si no se encuentra la mesa
     * @throws MesaYaOcupadaException : Si la mesa se encuentra ocupada
     */
    public boolean puedeEliminarMesa(int nroMesa) throws MesaNoEncontradaException, MesaYaOcupadaException;

    //METODOS PRODUCTOS

    /**
     * Retorna si el sistema puede eliminar un producto
     * @param idProducto : id del producto a eliminar
     * @return Si se puede eliminar un producto
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     */
    public boolean puedeEliminarProducto(int idProducto) throws ProductoNoEncontradoException, ProductoEnPedidoException;

}
