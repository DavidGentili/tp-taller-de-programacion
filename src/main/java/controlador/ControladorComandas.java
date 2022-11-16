package controlador;

import enums.FormasDePago;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.controlador.ErrorComandaException;
import exceptions.controlador.ErrorPromocionException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaLiberadaException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.operarios.NoSeCambioContraseniaException;
import exceptions.operarios.UsuarioNoLogueadoException;
import exceptions.productos.ProductoNoEncontradoException;
import helpers.PromocionHelpers;
import modelo.Empresa;
import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.Pedido;
import vista.interfaces.IVComanda;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ControladorComandas implements Observer, ActionListener, ListSelectionListener {

    private IVComanda vComanda;

    public ControladorComandas(IVComanda vistaComanda){
        this.vComanda = vistaComanda;
        Empresa.getInstance().addObserver(this);
        vComanda.setActionListenerComandas(this);
        vComanda.setSelectionListener(this);
        actualizaComandas();
    }

    @Override
    public void update(Observable o, Object arg) {
        actualizaComandas();
        actualizarPedido(vComanda.getSelectedComanda());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try{
            if(command.equals(Commands.AGREGAR_PEDIDO))
                agregarPedido();
            if(command.equals(Commands.CERRAR_COMANDA))
                cerrarComanda();
            if(command.equals(Commands.CERRAR_LOCAL))
                cerrarEmpresa();
            if(command.equals(Commands.ABRIR_LOCAL))
                abrirEmpresa();
        }catch (EmpresaAbiertaException ex){
            vComanda.showMessage("La empresa se encuentra abierta en este momento");
        }catch (Exception ex){
                System.out.println(ex);
                vComanda.showMessage(ex.getMessage());
        }
    }

    private void actualizarPedido(Comanda comanda) {
        if(comanda != null)
            vComanda.actuliazaPedido(comanda.getListaDePedidos());
        else
            vComanda.actuliazaPedido(new ArrayList<Pedido>());
    }

    private void actualizaComandas(){
        try{
            vComanda.actualizaComandas(Empresa.getInstance().getComandas());
        } catch (UsuarioNoLogueadoException ignored) {
            System.out.println(ignored);
        }
    }

    private void agregarPedido() throws ErrorComandaException, NoSeCambioContraseniaException, EmpresaCerradaException,
            UsuarioNoLogueadoException, ComandaYaCerradaException, ProductoNoEncontradoException,
            ComandaNoEncontradaException, MesaNoEncontradaException {
        int nroMesa = vComanda.getNroMesaComanda();
        int nroProd = vComanda.getNroProductoComanda();
        int cant = vComanda.getCantidadComanda();
        Comanda comanda = vComanda.getSelectedComanda();

        if(nroMesa < 0 && comanda == null)
            throw new ErrorComandaException("Debe ingresar un numero mesa o selecionar una comanda");
        if(nroProd < 0)
            throw new ErrorComandaException("Debe ingresar un numero de producto valido");
        if(cant < 0)
            throw new ErrorComandaException("Debe ingresar una cantidad valida");
        if(nroMesa < 0 && comanda != null)
            nroMesa = comanda.getMesa().getNroMesa();
        try{
            Empresa.getInstance().agregarComanda(nroMesa);
        } catch (MesaYaOcupadaException e) {
        }

        Empresa.getInstance().agregarPedido(nroMesa, nroProd, cant);
        vComanda.clearFieldComanda();
    }

    private void cerrarComanda() throws ErrorComandaException, ComandaYaCerradaException, NoSeCambioContraseniaException,
            EmpresaCerradaException, MesaNoEncontradaException, UsuarioNoLogueadoException, MesaYaLiberadaException {
        Comanda comanda = vComanda.getSelectedComanda();
        String pago = vComanda.getFormaDePagoComanda();
        if(comanda == null)
            throw new ErrorComandaException("Debe seleccionar la comanda que desea cerrar");
        try{
            FormasDePago formasDePago = PromocionHelpers.getFormaDePago(pago);
            Empresa.getInstance().cerrarComanda(comanda.getMesa().getNroMesa(), formasDePago);
        } catch (ErrorPromocionException e){
            throw new ErrorComandaException("Debe seleccionar una forma de pago");
        }
    }

    private void abrirEmpresa() throws NoSeCambioContraseniaException, NoHayMozosAsignadosException,
            CantidadMinimaDeProductosEnPromocionException, UsuarioNoLogueadoException,
            CantidadMaximaDeMozosActivosException, EmpresaAbiertaException, CantidadMinimaDeProductosException,
            CantidadMaximaDeMozosSuperadaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException {
        Empresa.getInstance().abrirEmpresa();
        vComanda.showMessage("Se abrio la empresa");

    }

    private void cerrarEmpresa() throws NoSeCambioContraseniaException, EmpresaCerradaException, HayComandasActivasException,
            UsuarioNoLogueadoException {
        Empresa.getInstance().cerrarEmpresa();
        vComanda.showMessage("Se cerro la empresa");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            JList list = (JList) e.getSource();
            Comanda selected = (Comanda) list.getSelectedValue();
            actualizarPedido(selected);
        }
    }
}
