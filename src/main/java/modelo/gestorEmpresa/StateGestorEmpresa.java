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

public interface StateGestorEmpresa {

    //METODOS CAMBIO DE ESTADO

    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException, CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException;

    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException;

    //METODOS ASIGNACION MOZO MEZA

    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws MozoNoActivoException, MesaYaOcupadaException, MozoNoEncontradoException, MesaNoEncontradaException, EmpresaAbiertaException;

    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException, MesaNoAsignadaException;

    //METODOS COMANDAS

    public void agregaComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException;

    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, ComandaYaCerradaException;

    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException, ComandaNoEncontradaException, ComandaYaCerradaException;

    //METODOS MOZOS
    public boolean puedeAgregarMozo() throws EmpresaAbiertaException;

    public boolean puedeDefinirEstadoMozo() throws EmpresaAbiertaException;

    public boolean puedeEliminarMozo(int idMozo) throws MozoNoEncontradoException, EmpresaAbiertaException;

    //METODOS MESAS
    public boolean puedeEliminarMesa(int nroMesa) throws MesaNoEncontradaException, MesaYaOcupadaException;

    //METODOS PRODUCTOS
    public boolean puedeEliminarProducto(int idProducto) throws ProductoNoEncontradoException, ProductoEnPedidoException;

}
