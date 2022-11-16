package vista.interfaces;

import modelo.configEmpresa.Operario;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVOperarios {
    public void setActionListenerOperarios(ActionListener a);
    public void clearFieldsOperario();
    public void actualizaOperarios(ArrayList<Operario> operarios);
    public String getNombreApellidoOperario();
    public String getNombreUsuarioOperario();
    public String getPasswordNuevoOperario();
    public void showMessage(String message);
    public String getPasswordCambiaContrasenia();
    public String getNuevoPasswordCambiaContrasenia();
    public void showMessageNotAutorizedOperarios(String message);
    public Operario getSelectedOperario();
}
