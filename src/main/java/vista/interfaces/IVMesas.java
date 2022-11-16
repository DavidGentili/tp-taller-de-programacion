package vista.interfaces;

import modelo.configEmpresa.Mesa;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVMesas {

    public void setActionListenerMesas(ActionListener e);
    public int getNroMesa();
    public int getCantSillas();
    public void actualizaMesas(ArrayList<Mesa> mesas);
    public Mesa getSelectedMesa();
    public void showMessage(String message);

    public void clearFieldsMesas();
}
