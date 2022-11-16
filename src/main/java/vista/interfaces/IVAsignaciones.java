package vista.interfaces;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.MozoMesa;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVAsignaciones {
    public void setActionListenerAsignacionesMozoMesa(ActionListener a);
    public Mesa getSelectedMesa();
    public Mozo getSelectedMozo();
    public MozoMesa getSelectedAsignacionMozoMesa();
    public void actualizarAsignacionesMozoMesa(ArrayList<MozoMesa> asignaciones);
}
