package modelo.gestorEmpresa;

import enums.EstadoMozos;
import exceptions.EmpresaCerradaException;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.GregorianCalendar;

public interface StateGestorEmpresa {

    //METODOS CAMBIO DE ESTADO

    public void abrirEmpresa();

    public void cerrarEmpresa() throws EmpresaCerradaException;

    //METODOS ASIGNACION MOZO MEZA

    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha);

    //METODOS COMANDAS

    public void agregaComanda(int nroMesa) throws EmpresaCerradaException;

    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException;

    public void agregarPedido(int nroMesa, Pedido pedido);

    //METODOS MOZOS
    public void agregarMozo(Mozo mozo, Operario user);

    public void definirEstadoMozo(int mozoId, EstadoMozos estado);

    public void eliminaMozo(int idMozo, Operario user);

    //METODOS MESAS
    public void eliminarMesa(int nroMesa, Operario user);

    //METODOS PRODUCTOS
    public void eliminarProducto(int idProducto, Operario user);

}
