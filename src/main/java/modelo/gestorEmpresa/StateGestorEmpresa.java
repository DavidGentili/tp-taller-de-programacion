package modelo.gestorEmpresa;

import enums.EstadoMozos;
import exceptions.*;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.gestorEmpresa.*;
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

public interface StateGestorEmpresa {

    //METODOS CAMBIO DE ESTADO

    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException;

    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException;

    //METODOS ASIGNACION MOZO MEZA

    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws MozoNoActivoException, MesaYaOcupadaException, MozoNoEncontradoException, MesaNoEncontradaException, EmpresaAbiertaException;

    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException;

    //METODOS COMANDAS

    public void agregaComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException;

    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, ComandaYaCerradaException;

    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException, ComandaNoEncontradaException, ComandaYaCerradaException;

    //METODOS MOZOS
    public void agregarMozo(Mozo mozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException;

    public void definirEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException;

    public void eliminaMozo(int idMozo, Operario user) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException;

    //METODOS MESAS
    public void eliminarMesa(int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException;

    //METODOS PRODUCTOS
    public void eliminarProducto(int idProducto, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException;

}
