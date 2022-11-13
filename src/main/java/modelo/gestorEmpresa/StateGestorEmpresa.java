package modelo.gestorEmpresa;

import enums.EstadoMozos;
import exceptions.*;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.GregorianCalendar;

public interface StateGestorEmpresa {

    //METODOS CAMBIO DE ESTADO

    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException;

    public void cerrarEmpresa() throws EmpresaCerradaException;

    //METODOS ASIGNACION MOZO MEZA

    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws MozoNoActivoException, MesaYaOcupadaException, MozoNoEncontradoException, MesaNoEncontradaException, EmpresaAbiertaException;

    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException;

    //METODOS COMANDAS

    public void agregaComanda(int nroMesa) throws EmpresaCerradaException;

    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException;

    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException;

    //METODOS MOZOS
    public void agregarMozo(Mozo mozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException;

    public void definirEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException;

    public void eliminaMozo(int idMozo, Operario user) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException;

    //METODOS MESAS
    public void eliminarMesa(int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException;

    //METODOS PRODUCTOS
    public void eliminarProducto(int idProducto, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException;

}
