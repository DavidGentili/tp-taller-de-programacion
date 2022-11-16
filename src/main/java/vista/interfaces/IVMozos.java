package vista.interfaces;

import modelo.configEmpresa.Mozo;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVMozos {
    public void setActionListenerMozo(ActionListener a);
    public void actualizarMozos(ArrayList<Mozo> mozos);
    public String getNombreApellidoMozo();
    public String getNacimientoMozo();
    public int getCantHijosMozo();
    public void showMessage(String message);
    public Mozo getSelectedMozo();
    public void clearFieldsMozo();
}
