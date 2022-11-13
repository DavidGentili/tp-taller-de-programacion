package modelo.gestorEmpresa;

import enums.EstadoMozos;
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
    public void abrirEmpresa() {

    }

    @Override
    public void cerrarEmpresa() throws EmpresaCerradaException {

    }

    @Override
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) {

    }

    @Override
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException {

    }

    @Override
    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException {

    }

    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) {

    }

    @Override
    public void agregarMozo(Mozo mozo, Operario user) {

    }

    @Override
    public void definirEstadoMozo(int mozoId, EstadoMozos estado) {

    }

    @Override
    public void eliminaMozo(int idMozo, Operario user) {

    }

    @Override
    public void eliminarMesa(int nroMesa, Operario user) {

    }

    @Override
    public void eliminarProducto(int idProducto, Operario user) {

    }
}
