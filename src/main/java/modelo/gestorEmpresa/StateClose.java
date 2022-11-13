package modelo.gestorEmpresa;

import config.Config;
import enums.EstadoMesas;
import enums.EstadoMozos;
import exceptions.*;
import helpers.MozoHelpers;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class StateClose implements StateGestorEmpresa{

    private GestorEmpresa empresa;

    public StateClose(GestorEmpresa empresa){
        assert empresa != null : "La empresa no debe ser nula";
        this.empresa = empresa;
    }

    @Override
    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException {
        if(empresa.getMozoMeza().size() == 0)
            throw new NoHayMozosAsignadosException("No hay mozos asignados a mesas");
        if(empresa.getProductos().size() > 0)
            throw new CantidadMinimaDeProductosException("No hay productos en la lista de productos");
        if(empresa.getPromocionesProducto().size() >= 2)
            throw new CantidadMinimaDeProductosEnPromocionException("Se debe posee al menos dos productos en promocion");
        ArrayList<Mozo> mozos = empresa.getMozos();
        if(mozos.size() >= Config.NUMERO_MAXIMO_DE_MOZOS)
            throw new CantidadMaximaDeMozosSuperadaException("Se puede tener como maximo " + Config.NUMERO_MAXIMO_DE_MOZOS + " mozos");
        if(MozoHelpers.getCantidadDeMozosEnEstado(mozos, EstadoMozos.ACTIVO) >= Config.NUMERO_MAXIMO_DE_MOZOS_ACTIVOS)
            throw new CantidadMaximaDeMozosActivosException("Se puede tener como maximo " + Config.NUMERO_MAXIMO_DE_MOZOS_ACTIVOS + " mozos activos");



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
        Mozo mozo = empresa.getMozoById(idMozo);
        Mesa mesa = empresa.getMesaByNroMesa(nroMesa);
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
    public void eliminarRelacionMozoMesa(int nroMesa) {
        assert nroMesa >= 0 : "El numero de mesa no puede ser mayor a 0";

        MozoMesa relacion = empresa.getMozoMezaByNroMesa(nroMesa);
        if(relacion != null)
            empresa.getMozoMeza().remove(relacion);
    }

    @Override
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException {
        throw new EmpresaCerradaException();
    }

    @Override
    public void agregarMozo(Mozo mozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException {
        empresa.getConfiguracion().agregaMozo(mozo, user);
    }

    @Override
    public void definirEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException {
        empresa.getConfiguracion().cambiarEstadoMozo(mozoId, estado);
    }

    @Override
    public void eliminaMozo(int idMozo, Operario user) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        empresa.getConfiguracion().eliminaMozo(idMozo, user);
    }

    @Override
    public void eliminarMesa(int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException {
        empresa.getConfiguracion().eliminarMesa(nroMesa, user);
        try{
            empresa.eliminarRelacionMozoMesa(nroMesa);
        }catch (EmpresaAbiertaException e){
        }
    }

    @Override
    public void eliminarProducto(int idProducto, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        empresa.getConfiguracion().eliminarProducto(idProducto, user);
    }
}
