package modelo.gestorEmpresa;

import enums.EstadoComanda;
import enums.EstadoMesas;
import enums.FormasDePago;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.factura.FacturaYaExistenteException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.MesaNoAsignadaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaLiberadaException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import helpers.FacturaHelpers;
import modelo.archivo.Archivo;
import modelo.archivo.Factura;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class StateOpen implements StateGestorEmpresa {


    private GestorEmpresa empresa;
    private ConfiguracionEmpresa configuracion;

    public StateOpen(GestorEmpresa empresa){
        assert empresa != null : "La empresa no debe ser nula";
        this.empresa = empresa;
        this.configuracion = ConfiguracionEmpresa.getInstance();
    }


    /**
     * Abre la empresa
     * @throws EmpresaAbiertaException : La empresa ya esta abierta
    */
    @Override
    public void abrirEmpresa() throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    /**
     * Se cierra la empresa, se limpian los estados de los mozos y se vacian las asignaciones
     * @throws HayComandasActivasException Si todavia existen comandas activas
     */
    @Override
    public void cerrarEmpresa() throws HayComandasActivasException {
        if(empresa.getComandas().size() > 0)
            throw new HayComandasActivasException();
        configuracion.clearEstadoMozos();
        empresa.limpiarAsignaciones();
        empresa.setState(new StateClose(empresa));
    }

    /**
     * Asigna un mozo a una mesa
     * @param idMozo : id del mozo
     * @param nroMesa : numero de la mesa
     * @param fecha : fecha de la asignacion
     * @throws EmpresaAbiertaException : La empresa ya esta abierta
     */
    @Override
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    /**
     * Elimina una relacion mozo con mesa
     * @param nroMesa : Numero de mesa a la cual desasignar
     */
    @Override
    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    /**
     * Agrega una nueva comanda al sistema
     * @param nroMesa Mesa a la cual asignar la nueva comanda
     * @throws MesaYaOcupadaException La mesa ya se encuentra ocupada
     */
    @Override
    public void agregaComanda(int nroMesa) throws MesaYaOcupadaException {
        assert nroMesa >= 0 : "El numero de mesa debe ser mayor a 0";

        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        ArrayList<Comanda> comandas = empresa.getComandas();
        Mesa mesa = configuracion.getMesaNroMesa(nroMesa);
        if(comanda != null || mesa.getEstado() == EstadoMesas.OCUPADA)
            throw new MesaYaOcupadaException();
        Comanda nueva = new Comanda(mesa);
        comandas.add(nueva);

        assert comandas.contains(nueva) : "No se agrego correctamente la nueva comanda";
    }

    /**
     * Se cierra una comanda, se crea la factura y se la almacena en archivo
     * @param nroMesa Numero de la mesa a la cual cerrar la comanda
     * @param formaDePago : La forma de pago
     * @throws MesaNoEncontradaException : Si no se encontro la mesa
     * @throws MesaYaLiberadaException : Si la mesa ya se encuentra liberada
     * @throws ComandaYaCerradaException : Si la comanda que se desea cerrar ya se encuentra cerrada
     */
    @Override
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws MesaNoEncontradaException, MesaYaLiberadaException, ComandaYaCerradaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";

        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        if(comanda == null)
            throw new MesaNoEncontradaException();
        try{
            comanda.getMesa().liberarMesa();
            comanda.cerrarComanda();
            ArrayList<Promocion> promocionesAplicadas = FacturaHelpers.getPromocionesAplicadas(empresa.getPromociones(), comanda.getListaDePedidos(), formaDePago);
            Factura factura = new Factura(comanda.getMesa(), comanda.getListaDePedidos(), promocionesAplicadas, formaDePago, comanda.getFecha());
            Archivo.getInstance().agregaFacturas(factura);

        } catch (ComandaYaCerradaException | FacturaYaExistenteException e) {
            try {
                comanda.getMesa().ocuparMesa();
                throw new ComandaYaCerradaException();
            } catch (MesaYaOcupadaException ex) {
            }
        }
        empresa.getComandas().remove(comanda);

        assert !empresa.getComandas().contains(comanda) : "No se retiro correctamente la comanda de la coleccion";
        assert comanda.getEstado() == EstadoComanda.CERRADA : "No se cerro correctamente la comanda";
        assert comanda.getMesa().getEstado() == EstadoMesas.LIBRE : "No se libero correctamente la mesa";

    }

    /**
     * Agrega un pedido a una comanda especifica
     * @param nroMesa numero de mesa a la cual adicionar el pedido
     * @param pedido : pedido a agregar
     * @throws ComandaNoEncontradaException : Si no se encuentra la comanda
     * @throws ComandaYaCerradaException : Si la comanda ya se encuentra cerrda
     */
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

    /**
     * Retorna si el sistema puede agregar un mozo
     * @return puede agregar un mozo
     * @throws EmpresaAbiertaException : Si la empresa se encuentra abierta
     */
    @Override
    public boolean puedeAgregarMozo() throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    /**
     * Retorna si el sistema puede definir el estado de un mozo
     * @return : puede definir el estado de unmozo
     * @throws EmpresaAbiertaException Si la empresa ya se encuentra abierta
     */
    @Override
    public boolean puedeDefinirEstadoMozo() throws EmpresaAbiertaException {
        throw new EmpresaAbiertaException();
    }

    /**
     * Retorna si el sistema puede eliminar un mozo
     * @param idMozo id del mozo que se quiere eliminar
     * @return : Sis se puede eliminar el mozo
     */
    @Override
    public boolean puedeEliminarMozo(int idMozo){
        return false;
    }

    /**
     * Retorna si el sistema puede eliminar una mesa
     * @param nroMesa numero de la mesa
     * @return Retorna si la mesa puede ser eliminada
     * @throws MesaNoEncontradaException : Si no se encuentra la mesa
     * @throws MesaYaOcupadaException : Si la mesa se encuentra ocupada
     */
    @Override
    public boolean puedeEliminarMesa(int nroMesa) throws MesaNoEncontradaException, MesaYaOcupadaException {
        Mesa mesa = configuracion.getMesaNroMesa(nroMesa);
        Comanda comanda = empresa.getComandaByNroMesa(nroMesa);
        if(mesa == null)
            throw new MesaNoEncontradaException();
        if(mesa.getEstado() == EstadoMesas.OCUPADA || comanda != null)
            throw new MesaYaOcupadaException();
        return true;
    }

    /**
     * Retorna si el sistema puede eliminar un producto
     * @param idProducto : id del producto a eliminar
     * @return Si se puede eliminar un producto
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     */
    @Override
    public boolean puedeEliminarProducto(int idProducto) throws ProductoEnPedidoException {
        boolean esta = false;
        int i = 0;
        while (i < empresa.getComandas().size() && !esta){
            if(empresa.getComandas().get(i).getProductoInPedido(idProducto))
                esta = true;
            i++;
        }
        if(esta)
            throw new ProductoEnPedidoException();
        return true;
    }

}
