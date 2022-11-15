package modelo.gestorEmpresa;

import config.Config;
import enums.EstadoMozos;
import enums.FormasDePago;
import exceptions.*;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.MesaNoAsignadaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.UsuarioNoAutorizadoException;
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

    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

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

    @Override
    public void eliminarRelacionMozoMesa(int nroMesa) throws MesaNoAsignadaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser mayor a 0";

        MozoMesa relacion = empresa.getMozoMezaByNroMesa(nroMesa);
        if(relacion == null)
            throw new MesaNoAsignadaException();
        empresa.getMozoMeza().remove(relacion);

        assert !empresa.getMozoMeza().contains(relacion) : "No se elimino correctamente la relacion mozo mesa";
    }

    @Override
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public boolean puedeAgregarMozo(){
        return true;
    }

    @Override
    public boolean puedeDefinirEstadoMozo(){
        return true;
    }


    @Override
    public boolean puedeEliminarMozo(int idMozo) {
        return true;
    }

    @Override
    public boolean puedeEliminarMesa(int nroMesa) {
        return true;
    }

    @Override
    public boolean puedeEliminarProducto(int idProducto) {
        return true;
    }
}
