package vista.interfaces;

import modelo.configEmpresa.Sueldo;

import java.awt.event.ActionListener;

public interface IVConfiguracion {
    public void setActionListenerConfiguracion(ActionListener a);
    public String getNombreLocal();
    public double getBasico();
    public double getBonificacion();
    public void clearFieldsConfiguracion();
    public void setNombreEmpresa(String name);
    public void setSueldo(Sueldo sueldo);
}
