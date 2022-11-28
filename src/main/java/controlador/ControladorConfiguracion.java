package controlador;

import exceptions.IdIncorrectoException;
import exceptions.controlador.ErrorConfiguracionException;
import exceptions.controlador.ErrorOperarioException;
import exceptions.operarios.*;
import helpers.OperarioHelpers;
import modelo.Empresa;
import modelo.configEmpresa.Operario;
import modelo.configEmpresa.Sueldo;
import vista.interfaces.IVConfiguracion;
import vista.interfaces.IVOperarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ControladorConfiguracion implements Observer, ActionListener {

    IVOperarios vOperarios;
    IVConfiguracion vConfiguracion;

    public ControladorConfiguracion(IVOperarios vistaOperarios, IVConfiguracion vistaConfiguracion){
        this.vOperarios = vistaOperarios;
        this.vConfiguracion = vistaConfiguracion;
        vOperarios.setActionListenerOperarios(this);
        vConfiguracion.setActionListenerConfiguracion(this);
        Empresa.getInstance().addObserver(this);

        actualizaOperario();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try{
            if(command.equals(Commands.AGREGAR_OPERARIO))
                agregarOperario();
            if(command.equals(Commands.ELIMINAR_OPERARIO))
                eliminarOperario();
            if(command.equals(Commands.CAMBIAR_CONTRASENIA))
                cambiarContrasenia();
            if(command.equals(Commands.CAMBIAR_NOMBRE_LOCAL))
                cambiarNombreLocal();
            if(command.equals(Commands.CAMBIAR_SUELDO))
                cambiarSueldo();
            if(command.equals(Commands.LOGOUT))
                cerrarSesion();
        } catch (ErrorOperarioException | NoSeCambioContraseniaException | UsuarioNoLogueadoException |
                 UsuarioNoAutorizadoException | OperarioYaExistenteException | OperarioNoEncontradoException |
                 IdIncorrectoException | EliminarOperarioLogueadoException | ContraseniaIncorrectaException |
                 ErrorConfiguracionException ex) {
            vOperarios.showMessage(ex.getMessage());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        actualizaOperario();
        actualizaConfiguracion();
    }

    private void actualizaOperario(){
        try{
            ArrayList<Operario> operarios = Empresa.getInstance().getOperarios();
            vOperarios.actualizaOperarios(operarios);
        } catch (UsuarioNoLogueadoException | UsuarioNoAutorizadoException e) {
            vOperarios.showMessageNotAutorizedOperarios("El operario actual no se encuentra autorizado a ver a los otros operarios");
        }
    }

    private void agregarOperario() throws ErrorOperarioException, NoSeCambioContraseniaException, OperarioYaExistenteException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        String nombreOperario = vOperarios.getNombreApellidoOperario();
        String nombreUsuario = vOperarios.getNombreUsuarioOperario();
        String password = vOperarios.getPasswordNuevoOperario();

        if(nombreOperario == null || nombreOperario.isBlank() || nombreOperario.isEmpty())
            throw new ErrorOperarioException("El nombre y apellido no es valido");
        if(nombreUsuario == null || nombreUsuario.isBlank() || nombreUsuario.isEmpty())
            throw new ErrorOperarioException("El nombre de usuario no es valido");
        if(!OperarioHelpers.correctPassword(password))
            throw new ErrorOperarioException("La contraseña no es correcta");

        Operario op = new Operario(nombreOperario,nombreUsuario,password);
        Empresa.getInstance().agregarOperario(op);
        vOperarios.clearFieldsOperario();
    }

    private void eliminarOperario() throws NoSeCambioContraseniaException, OperarioNoEncontradoException, UsuarioNoLogueadoException, IdIncorrectoException, UsuarioNoAutorizadoException, ErrorOperarioException, EliminarOperarioLogueadoException {
        Operario op = vOperarios.getSelectedOperario();
        if(op == null)
            throw new ErrorOperarioException("Debe seleccionar un operario de la lista");
        Empresa.getInstance().eliminarOperario(op.getId());
    }

    private void cambiarContrasenia() throws ErrorOperarioException, UsuarioNoLogueadoException, OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException, IdIncorrectoException {
        String password = vOperarios.getPasswordCambiaContrasenia();
        String newPassword = vOperarios.getNuevoPasswordCambiaContrasenia();

        if(!OperarioHelpers.correctPassword(password))
            throw new ErrorOperarioException("El formato de la contraseña actual es incorrecto");
        if(!OperarioHelpers.correctPassword(newPassword))
            throw new ErrorOperarioException("El formato de la nueva contraseña no es correcto");
        Empresa.getInstance().cambiarContraseniaOperario(password, newPassword, Empresa.getInstance().getIdUsuario());
        vOperarios.clearFieldsOperario();
    }

    private void cerrarSesion(){
        try {
            Empresa.getInstance().logout();
        } catch (UsuarioNoLogueadoException ignored) {

        }
    }

    private void actualizaConfiguracion(){
        try{
            vConfiguracion.setNombreEmpresa(Empresa.getInstance().getNombreLocal());
            vConfiguracion.setSueldo(Empresa.getInstance().getSueldo());
        } catch (UsuarioNoLogueadoException ignored) {
            vConfiguracion.setNombreEmpresa("No es posible acceder a esta informacion");
            vConfiguracion.setSueldo(null);
        }
    }

    private void cambiarSueldo() throws ErrorConfiguracionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        double basico = vConfiguracion.getBasico();
        double bonificacion = vConfiguracion.getBonificacion();

        if(basico < 0)
            throw new ErrorConfiguracionException("El basico es incorrecto");
        if(bonificacion < 0)
            throw new ErrorConfiguracionException("La bonificacion no es correcta");

        Sueldo sueldo = new Sueldo(basico,bonificacion);
        Empresa.getInstance().setSueldo(sueldo);
        vConfiguracion.clearFieldsConfiguracion();
    }

    private void cambiarNombreLocal() throws ErrorConfiguracionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        String nombre = vConfiguracion.getNombreLocal();

        if(nombre == null || nombre.isEmpty() || nombre.isBlank())
            throw new ErrorConfiguracionException("Debe seleccionar un nombre para el local");
        Empresa.getInstance().cambiarNombreLocal(nombre);
        vConfiguracion.clearFieldsConfiguracion();
    }
}
