package controlador;

import enums.EstadoMozos;
import exceptions.IdIncorrectoException;
import exceptions.controlador.*;
import exceptions.gestorEmpresa.EmpresaAbiertaException;
import exceptions.mesas.MesaNoAsignadaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaExistenteException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.NoSeCambioContraseniaException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import exceptions.operarios.UsuarioNoLogueadoException;
import helpers.MozoHelpers;
import modelo.Empresa;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.MozoMesa;
import vista.interfaces.IVAsignaciones;
import vista.interfaces.IVMesas;
import vista.interfaces.IVMozos;
import vista.interfaces.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

public class ControladorSalon implements ActionListener, Observer {

    IVMesas vMesas;
    IVMozos vMozos;

    IVAsignaciones vAsignaciones;

    public ControladorSalon(IVMesas vistaMesa, IVMozos vistaMozo, IVAsignaciones vistaAsignaciones) {
        this.vMesas = vistaMesa;
        this.vMozos = vistaMozo;
        this.vAsignaciones = vistaAsignaciones;
        Empresa.getInstance().addObserver(this);
        vMesas.setActionListenerMesas(this);
        vMozos.setActionListenerMozo(this);
        vistaAsignaciones.setActionListenerAsignacionesMozoMesa(this);
        actualizaMesas();
        actualizarMozos();
        actualizarAsignacionesMozoMesa();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try{
            if(command.equals(Commands.AGREGAR_MESA))
                agregarMesa();
            if(command.equals(Commands.ELIMINAR_MESA))
                eliminarMesa();
            if(command.equals(Commands.AGREGAR_MOZO))
                agregarMozo();
            if(command.equals(Commands.ELIMINAR_MOZO))
                eliminarMozo();
            if(command.equals(Commands.AGREGAR_ASIGNACION))
                agregaAsignacion();
            if(command.equals(Commands.ELIMINAR_ASIGNACION))
                eliminarAsignacion();
            if(command.equals(Commands.DEFINIR_ESTADO_MOZO))
                definirEstadoMozo();

        }catch (ErrorAlAgregarMesaException | NoSeCambioContraseniaException | MesaYaExistenteException |
                UsuarioNoLogueadoException | UsuarioNoAutorizadoException | MesaNoEncontradaException |
                IdIncorrectoException | ErrorAlEliminarMesaException | MesaYaOcupadaException |
                MozoYaAgregadoException | EmpresaAbiertaException | ErrorAlAgregarMozoException |
                ErrorAlEliminarMozoException | MozoNoEncontradoException | ErrorAlEliminarUnaAsignacionException |
                MesaNoAsignadaException | ErrorAlAgregarUnaAsignacionException | MozoNoActivoException |
                ErrorAlDefinirEstadoMozoException ex) {
            System.out.println(ex);
            vMesas.showMessage(ex.getMessage());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        actualizaMesas();
        actualizarMozos();
        actualizarAsignacionesMozoMesa();
    }

    private void agregarMesa() throws ErrorAlAgregarMesaException, NoSeCambioContraseniaException, MesaYaExistenteException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        int nroMesa = vMesas.getNroMesa();
        int cantSillas = vMesas.getCantSillas();
        if(nroMesa < 0 || nroMesa > 1000)
            throw new ErrorAlAgregarMesaException("Numero de mesa incorrecto");
        if(cantSillas < 0 || cantSillas > 1000)
            throw new ErrorAlAgregarMesaException("La cantidad de sillas es incorrecta");
        Empresa.getInstance().agregarMesa(new Mesa(nroMesa, cantSillas));
        vMesas.clearFieldsMesas();
    }

    private void eliminarMesa() throws ErrorAlEliminarMesaException, NoSeCambioContraseniaException, MesaNoEncontradaException, UsuarioNoLogueadoException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException {
        Mesa mesa = vMesas.getSelectedMesa();
        if(mesa == null)
            throw new ErrorAlEliminarMesaException("Debe seleccionar una mesa de la lista");
        Empresa.getInstance().eliminarMesa(mesa.getNroMesa());
    }

    private void agregarMozo() throws NoSeCambioContraseniaException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException, ErrorAlAgregarMozoException {
        String nombreYApellido = vMozos.getNombreApellidoMozo();
        String nacimiento = vMozos.getNacimientoMozo();
        int cantHijos = vMozos.getCantHijosMozo();

        MozoHelpers.checkNombreYApellido(nombreYApellido);
        GregorianCalendar fecha = MozoHelpers.getFechaDeNacimiento(nacimiento);
        MozoHelpers.checkCantHijos(cantHijos);

        Mozo mozo = new Mozo(nombreYApellido, fecha, cantHijos);

        Empresa.getInstance().agregarMozo(mozo);
        vMozos.clearFieldsMozo();

    }

    private void eliminarMozo() throws ErrorAlEliminarMozoException, NoSeCambioContraseniaException, MozoNoEncontradoException, UsuarioNoLogueadoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException {
        Mozo mozo = vMozos.getSelectedMozo();
        if(mozo == null)
            throw new ErrorAlEliminarMozoException("Debe selecciona un mozo a eliminar");
        Empresa.getInstance().eliminarMozo(mozo.getId());
    }

    private void actualizaMesas(){
        try{
            vMesas.actualizaMesas(Empresa.getInstance().getMesas());
        }catch (UsuarioNoLogueadoException e) {

        }
    }

    private void actualizarMozos(){
        try{
            vMozos.actualizarMozos(Empresa.getInstance().getMozos());
        } catch (UsuarioNoLogueadoException ignored) {

        }
    }

    private void agregaAsignacion() throws ErrorAlAgregarUnaAsignacionException, NoSeCambioContraseniaException, MesaNoEncontradaException, MozoNoEncontradoException, UsuarioNoLogueadoException, MozoNoActivoException, EmpresaAbiertaException, MesaYaOcupadaException {
        Mozo mozo = vAsignaciones.getSelectedMozo();
        Mesa mesa = vAsignaciones.getSelectedMesa();

        if(mozo == null)
            throw new ErrorAlAgregarUnaAsignacionException("Debe seleciconar un mozo");
        if(mesa == null)
            throw new ErrorAlAgregarUnaAsignacionException("Debe Seleccionar una mesa");
        Empresa.getInstance().asignaMozo(mozo.getId(), mesa.getNroMesa(), (GregorianCalendar) GregorianCalendar.getInstance());
    }

    private void eliminarAsignacion() throws ErrorAlEliminarUnaAsignacionException, NoSeCambioContraseniaException, MesaNoAsignadaException, UsuarioNoLogueadoException, EmpresaAbiertaException {
        MozoMesa asignacion = vAsignaciones.getSelectedAsignacionMozoMesa();
        if(asignacion == null)
            throw new ErrorAlEliminarUnaAsignacionException("Debe seleccionar la asignacion a eliminar");
        Empresa.getInstance().eliminarRelacionMozoMeza(asignacion.getMesa().getNroMesa());
    }

    private void actualizarAsignacionesMozoMesa(){
        try{
            vAsignaciones.actualizarAsignacionesMozoMesa(Empresa.getInstance().getAsignacionMozoMeza());
        } catch (UsuarioNoLogueadoException ignored) {

        }
    }

    private void definirEstadoMozo() throws ErrorAlDefinirEstadoMozoException {
        String estado = vMozos.getEstadoMozo();
        Mozo mozo = vMozos.getSelectedMozo();

        if(estado == null)
            throw new ErrorAlDefinirEstadoMozoException("Debe seleccionar un estado");
        if(mozo == null)
            throw new ErrorAlDefinirEstadoMozoException("Debe seleccionar un mozo");
        try{
            EstadoMozos nuevoEstado = EstadoMozos.valueOf(estado);
            Empresa.getInstance().cambiarEstadoMozo(mozo.getId(), nuevoEstado);
        }catch (Exception e){
            throw new ErrorAlDefinirEstadoMozoException("Estado incorrecto");
        }
    }
}
