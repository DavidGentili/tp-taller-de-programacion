package modelo.gestorEmpresa;

import enums.EstadoComanda;
import enums.EstadoMesas;
import enums.EstadoMozos;
import exceptions.*;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;

import java.util.ArrayList;
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
    public void cerrarEmpresa() throws HayComandasActivasException {
        if(empresa.getComandas().size() > 0)
            throw new HayComandasActivasException();
        //ALMACENAR INFORMACION DE LA JORNADA EN ARCHIVO
        empresa.getConfiguracion().clearEstadoMozos();
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
    public void agregaComanda(int nroMesa) throws EmpresaCerradaException, MesaYaOcupadaException {
        assert nroMesa >= 0 : "El numero de mesa debe ser mayor a 0";

        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        ArrayList<Comanda> comandas = empresa.getComandas();
        Mesa mesa = empresa.getMesaByNroMesa(nroMesa);
        if(comanda != null || mesa.getEstado() == EstadoMesas.OCUPADA)
            throw new MesaYaOcupadaException();
        Comanda nueva = new Comanda(mesa);
        comandas.add(nueva);

        assert comandas.contains(nueva) : "No se agrego correctamente la nueva comanda";
    }

    @Override
    public void cerrarComanda(int nroMesa) throws EmpresaCerradaException, MesaNoEncontradaException, MesaYaLiberadaException, ComandaYaCerradaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";

        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        if(comanda == null)
            throw new MesaNoEncontradaException();
        try{
            comanda.getMesa().liberarMesa();
            comanda.cerrarComanda();
        } catch (ComandaYaCerradaException e){
            try {
                comanda.getMesa().ocuparMesa();
                throw new ComandaYaCerradaException();
            } catch (MesaYaOcupadaException ex) {
            }
        }

        empresa.getComandas().remove(comanda);

        //FACTURAR : Falta modulo facturacion para conocer correctamente la interface;

        assert !empresa.getComandas().contains(comanda) : "No se retiro correctamente la comanda de la coleccion";
        assert comanda.getEstado() == EstadoComanda.CERRADA : "No se cerro correctamente la comanda";
        assert comanda.getMesa().getEstado() == EstadoMesas.LIBRE : "No se libero correctamente la mesa";

    }

    @Override
    public void agregarPedido(int nroMesa, Pedido pedido) throws ComandaNoEncontradaException, ComandaYaCerradaException {
        assert pedido != null : "El pedido no puede ser nulo";
        assert nroMesa >= 0 : "El nro de meso no puede ser nulo";

        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        if(comanda == null)
            throw new ComandaNoEncontradaException();
        comanda.agregarPedido(pedido);

        assert comanda.getListaDePedidos().contains(pedido) : "No se agrego correctamente el pedido";
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
    public void eliminarMesa(int nroMesa, Operario user) throws MesaNoEncontradaException, MesaYaOcupadaException, IdIncorrectoException, UsuarioNoAutorizadoException {
        assert user != null : "El usuario no puede ser nulo";
        Mesa mesa = empresa.getMesaByNroMesa(nroMesa);
        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        if(mesa == null)
            throw new MesaNoEncontradaException();
        if(mesa.getEstado() == EstadoMesas.OCUPADA || comanda != null)
            throw new MesaYaOcupadaException();
        empresa.getConfiguracion().eliminarMesa(nroMesa, user);
    }

    @Override
    public void eliminarProducto(int idProducto, Operario user) throws ProductoEnPedidoException, ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        assert idProducto >= 0 : "El id no puede ser negativo";
        assert user != null : "El usuario no puede ser nulo";

        boolean esta = false;
        int i = 0;
        while (i < empresa.getComandas().size() && !esta){
            if(empresa.getComandas().get(i).getProductoInPedido(idProducto))
                esta = true;
            i++;
        }
        if(esta)
            throw new ProductoEnPedidoException();
        empresa.configuracion.eliminarProducto(idProducto, user);
    }
}
