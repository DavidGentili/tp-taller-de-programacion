package modelo.gestorEmpresa;

import config.Config;
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
import helpers.MozoHelpers;
import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class StateClose implements StateGestorEmpresa{

    private GestorEmpresa empresa;
    private ConfiguracionEmpresa configuracion;

    public StateClose(GestorEmpresa empresa){
        assert empresa != null : "La empresa no debe ser nula";
        this.empresa = empresa;
        this.configuracion = ConfiguracionEmpresa.getInstance();
    }

    /**
     * Abre la empresa
     * @throws NoHayMozosAsignadosException : No hay mozos asignados
     * @throws CantidadMinimaDeProductosException : No se cumple la cantidad minima de productos
     * @throws CantidadMinimaDeProductosEnPromocionException : No se cumple la cantidad minima de productos en promocion
     * @throws CantidadMaximaDeMozosSuperadaException : Se supero la cantidad maxima de mozos
     * @throws CantidadMaximaDeMozosActivosException : Se supero la cantida maxima de mozos activo
     * @throws CantidadMaximaDeMozosDeFrancoException : Se supero la cantidad maxima de mozos de franco
     * @throws HayMozoSinEstadoAsignadoException : Hay mozos sin estado asignado
     */
    @Override
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException {
        if(empresa.getMozoMeza().size() == 0)
            throw new NoHayMozosAsignadosException("No hay mozos asignados a mesas");
        if(configuracion.getProductos().size() > 0)
            throw new CantidadMinimaDeProductosException("No hay productos en la lista de productos");
        if(empresa.getPromocionesProducto().size() >= 2)
            throw new CantidadMinimaDeProductosEnPromocionException("Se debe posee al menos dos productos en promocion");
        ArrayList<Mozo> mozos = configuracion.getMozos();
        if(MozoHelpers.thereIsMozoWithoutState(mozos))
            throw new HayMozoSinEstadoAsignadoException("Todos los mozos deben tener un estado asignado");
        if(mozos.size() >= Config.NUMERO_MAXIMO_DE_MOZOS)
            throw new CantidadMaximaDeMozosSuperadaException("Se puede tener como maximo " + Config.NUMERO_MAXIMO_DE_MOZOS + " mozos");
        if(MozoHelpers.getCantidadDeMozosEnEstado(mozos, EstadoMozos.ACTIVO) >= Config.NUMERO_MAXIMO_DE_MOZOS_ACTIVOS)
            throw new CantidadMaximaDeMozosActivosException("Se puede tener como maximo " + Config.NUMERO_MAXIMO_DE_MOZOS_ACTIVOS + " mozos activos");
        if(MozoHelpers.getCantidadDeMozosEnEstado(mozos, EstadoMozos.DE_FRANCO) >= Config.NUMERO_MAXIMO_DE_MOZOS_DE_FRANCO)
            throw new CantidadMaximaDeMozosDeFrancoException("Se puede tener como maximo " + Config.NUMERO_MAXIMO_DE_MOZOS_DE_FRANCO + " mozos de franco");
        Archivo.getInstance().agregaRegistroDeAsistencia(mozos, (GregorianCalendar) GregorianCalendar.getInstance());
        Archivo.getInstance().agregaMozoMesa(empresa.getMozoMeza());
        empresa.setState(new StateOpen(empresa));
    }

    /**
     * Se cierra la empresa, se limpian los estados de los mozos y se vacian las asignaciones
     * @throws EmpresaCerradaException Si la empresa ya esta cerrada
     */
    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    /**
     * Asigna un mozo a una mesa
     * @param idMozo : id del mozo
     * @param nroMesa : numero de la mesa
     * @param fecha : fecha de la asignacion
     * @throws MozoNoActivoException : El mozo asignado no esta activo
     * @throws MesaYaOcupadaException : La mesa ya esta ocupada
     * @throws MozoNoEncontradoException : No se encontro el mozo con ese id
     * @throws MesaNoEncontradaException : No se encontro mesa con ese numero de mesa
     */
    @Override
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws MozoNoActivoException, MesaYaOcupadaException, MozoNoEncontradoException, MesaNoEncontradaException {
        assert idMozo >= 0 : "El id del mozo no puede ser negativo";
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        Mozo mozo = configuracion.getMozoById(idMozo);
        Mesa mesa = configuracion.getMesaNroMesa(nroMesa);
        if(mozo == null)
            throw new MozoNoEncontradoException();
        if(mozo.getEstado() != EstadoMozos.ACTIVO)
            throw new MozoNoActivoException();
        if(mesa == null)
            throw new MesaNoEncontradaException();
        if(empresa.getMozoMezaByNroMesa(nroMesa) != null)
            throw new MesaYaOcupadaException();
        empresa.getMozoMeza().add(new MozoMesa(fecha, mozo, mesa));
    }

    /**
     * Elimina una relacion mozo con mesa
     * @param nroMesa : Numero de mesa a la cual desasignar
     * @throws MesaNoAsignadaException : Si no existe dicha asignacion
     */
    @Override
    public void eliminarRelacionMozoMesa(int nroMesa) throws MesaNoAsignadaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser mayor a 0";

        MozoMesa relacion = empresa.getMozoMezaByNroMesa(nroMesa);
        if(relacion == null)
            throw new MesaNoAsignadaException();
        empresa.getMozoMeza().remove(relacion);

        assert !empresa.getMozoMeza().contains(relacion) : "No se elimino correctamente la relacion mozo mesa";
    }

    /**
     * Agrega una nueva comanda al sistema
     * @param nroMesa Mesa a la cual asignar la nueva comanda
     * @throws EmpresaCerradaException La empresa ya se encuentra cerrada
     */
    @Override
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    /**
     * Se cierra una comanda, se crea la factura y se la almacena en archivo
     * @param nroMesa Numero de la mesa a la cual cerrar la comanda
     * @param formaDePago : La forma de pago
     * @throws EmpresaCerradaException : La empresa ya se encuentra cerrada
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    /**
     * Agrega un pedido a una comanda especifica
     * @param nroMesa numero de mesa a la cual adicionar el pedido
     * @param pedido : pedido a agregar
     * @throws EmpresaCerradaException : Si la empresa se encuentra cerrada
     */
    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    /**
     * Retorna si el sistema puede agregar un mozo
     * @return puede agregar un mozo
     */
    @Override
    public boolean puedeAgregarMozo(){
        return true;
    }

    /**
     * Retorna si el sistema puede definir el estado de un mozo
     * @return : puede definir el estado de unmozo
     */
    @Override
    public boolean puedeDefinirEstadoMozo(){
        return true;
    }

    /**
     * Retorna si el sistema puede eliminar un mozo
     * @param idMozo id del mozo que se quiere eliminar
     * @return : Sis se puede eliminar el mozo
     */
    @Override
    public boolean puedeEliminarMozo(int idMozo) {
        return true;
    }

    /**
     * Retorna si el sistema puede eliminar una mesa
     * @param nroMesa numero de la mesa
     * @return Retorna si la mesa puede ser eliminada
     */
    @Override
    public boolean puedeEliminarMesa(int nroMesa) {
        return true;
    }

    /**
     * Retorna si el sistema puede eliminar un producto
     * @param idProducto : id del producto a eliminar
     * @return Si se puede eliminar un producto
     */
    @Override
    public boolean puedeEliminarProducto(int idProducto) {
        return true;
    }
}
