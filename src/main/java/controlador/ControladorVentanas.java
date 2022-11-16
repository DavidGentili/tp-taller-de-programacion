package controlador;

import exceptions.operarios.*;
import modelo.Empresa;
import modelo.configEmpresa.ConfiguracionEmpresa;
import vista.VentanLogin;
import vista.VentanaEmpresa;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

public class ControladorVentanas implements Observer, WindowListener {

    VentanLogin vLogin;
    VentanaEmpresa vEmpresa;
    Empresa empresa;
    ControladorLogin cLogin;
    ControladorSalon cSalon;
    ControladorProductos cProductos;
    ControladorArchivo cArchivo;


    public ControladorVentanas(){
        empresa = Empresa.getInstance();
        empresa.addObserver(this);
//        abreVentanaLogin();
        pruebaInitialState();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(empresa.isLogin() && vEmpresa == null)
            abreVentanaEmpresa();
        if(!empresa.isLogin() && vLogin == null)
            abreVentanaLogin();

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void abreVentanaLogin(){
        if(vEmpresa != null){
            vEmpresa.removeWindowListener(this);
            vEmpresa.dispose();
            vEmpresa = null;
        }

        vLogin = new VentanLogin();
        vLogin.setVisible(true);
        vLogin.addWindowListener(this);
        cLogin = new ControladorLogin(vLogin);
    }

    private void abreVentanaEmpresa(){
        if(vLogin != null){
            vLogin.removeWindowListener(this);
            vLogin.dispose();
            vLogin = null;
        }
        vEmpresa = new VentanaEmpresa();
        vEmpresa.setVisible(true);
        vEmpresa.addWindowListener(this);
        cSalon = new ControladorSalon(vEmpresa, vEmpresa, vEmpresa);
        cProductos = new ControladorProductos(vEmpresa, vEmpresa);
        cArchivo = new ControladorArchivo(vEmpresa);

    }

    private void pruebaInitialState(){
        try{
            empresa.login("ADMIN","ADMIN1234");
            empresa.cambiarContraseniaOperario("ADMIN1234", "ADMIN12345", 0);
        } catch (OperarioInactivoException | UsuarioYaLogueadoException | DatosLoginIncorrectosException e) {
            System.out.println("ERROR EN LOGIN FALSO");
        } catch (OperarioNoEncontradoException | UsuarioNoLogueadoException | ContraseniaIncorrectaException |
                 UsuarioNoAutorizadoException e) {
            System.out.println("ERROR EN CAMBIAR CONTRASEÑA FALSO");
            System.out.println(e);

        }

    }
}
