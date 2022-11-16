package controlador;

import exceptions.IdIncorrectoException;
import exceptions.controlador.ErrorOperarioException;
import exceptions.operarios.*;
import helpers.OperarioHelpers;
import modelo.Empresa;
import modelo.configEmpresa.Operario;
import vista.interfaces.IVOperarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ControladorConfiguracion implements Observer, ActionListener {

    IVOperarios vOperarios;

    public ControladorConfiguracion(IVOperarios vistaOperarios){
        this.vOperarios = vistaOperarios;
        vistaOperarios.setActionListenerOperarios(this);
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
        } catch (ErrorOperarioException | NoSeCambioContraseniaException | UsuarioNoLogueadoException |
                 UsuarioNoAutorizadoException | OperarioYaExistenteException | OperarioNoEncontradoException |
                 IdIncorrectoException | EliminarOperarioLogueadoException | ContraseniaIncorrectaException ex) {
            vOperarios.showMessage(ex.getMessage());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        actualizaOperario();
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

    private void cambiarContrasenia() throws ErrorOperarioException, UsuarioNoLogueadoException, OperarioNoEncontradoException, ContraseniaIncorrectaException, UsuarioNoAutorizadoException {
        String password = vOperarios.getPasswordCambiaContrasenia();
        String newPassword = vOperarios.getNuevoPasswordCambiaContrasenia();

        if(!OperarioHelpers.correctPassword(password))
            throw new ErrorOperarioException("El formato de la contraseña actual es incorrecto");
        if(!OperarioHelpers.correctPassword(newPassword))
            throw new ErrorOperarioException("El formato de la nueva contraseña no es correcto");
        Empresa.getInstance().cambiarContraseniaOperario(password, newPassword, Empresa.getInstance().getIdUsuario());
        vOperarios.clearFieldsOperario();
    }
}
