package controlador;

import exceptions.controlador.LoginException;
import exceptions.operarios.DatosLoginIncorrectosException;
import exceptions.operarios.OperarioInactivoException;
import exceptions.operarios.UsuarioYaLogueadoException;
import helpers.OperarioHelpers;
import modelo.Empresa;
import vista.interfaces.IVLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin implements ActionListener {
    IVLogin vista;

    public ControladorLogin(IVLogin vista){
        this.vista = vista;
        vista.setActionListenerLogin(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == Commands.LOGIN)
            login();
    }

    private void checkUserName(String userName) throws LoginException {
        if(userName == null || userName.isEmpty() || userName.isBlank())
            throw new LoginException("Nombre de usuario incorrecto");
    }

    private void checkPassword(String password) throws LoginException {
        if(!OperarioHelpers.correctPassword(password))
            throw new LoginException("La contraseña no cumple con los parametros indicados");

    }

    private void login(){
        String userName = vista.getUserName();
        String password = vista.getPassword();
        try{
            checkUserName(userName);
            checkPassword(password);
            Empresa.getInstance().login(userName,password);
        } catch (OperarioInactivoException e) {
            vista.setMesssage("El operario ingresado esta inactivo");
        } catch (LoginException e) {
            vista.setMesssage(e.getMessage());
        } catch (UsuarioYaLogueadoException | DatosLoginIncorrectosException e) {
            vista.setMesssage("Datos de login incorrectos");
        }


    }
}
