package modelo.gestorEmpresa;

import enums.EstadoMozos;
import exceptions.EmpresaAbiertaException;
import exceptions.EmpresaCerradaException;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.GregorianCalendar;

public class StateOpen implements StateGestorEmpresa{

    private GestorEmpresa empresa;

    public StateOpen(GestorEmpresa empresa){
        assert empresa != null : "La empresa no debe ser nula";
        this.empresa = empresa;
    }


    @Override
    public void abrirEmpresa() throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException {
        //IMPLEMENTAR
    }

    @Override
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException {
        //IMPLENTAR
    }

    @Override
    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException {
        //IMPLENTAR
    }

    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) {
        //IMPLENTAR
    }

    @Override
    public void agregarMozo(Mozo mozo, Operario user) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void definirEstadoMozo(int mozoId, EstadoMozos estado) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void eliminaMozo(int idMozo, Operario user) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    @Override
    public void eliminarMesa(int nroMesa, Operario user) {
        //IMPLENTAR
    }

    @Override
    public void eliminarProducto(int idProducto, Operario user) {
        //IMPLENTAR
    }
}
