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
    ControladorConfiguracion cConfiguracion;
    ControladorComandas cComandas;

    public ControladorVentanas(){
        empresa = Empresa.getInstance();
        empresa.addObserver(this);
        abreVentanaLogin();
//        pruebaInitialState();
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
        empresa.guardarEstado();
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
        cConfiguracion = new ControladorConfiguracion(vEmpresa, vEmpresa);
        cComandas = new ControladorComandas(vEmpresa);

    }

    private void pruebaInitialState(){
        try{
            empresa.login("ADMIN","Admin1234");
        } catch (OperarioInactivoException | UsuarioYaLogueadoException | DatosLoginIncorrectosException e) {
            System.out.println("ERROR FALSO LOGIN");
        }

    }
}
