package vista.interfaces;

import modelo.configEmpresa.Producto;
import modelo.gestorEmpresa.Promocion;
import modelo.gestorEmpresa.PromocionProducto;
import modelo.gestorEmpresa.PromocionTemp;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVPromociones {
    public void setActionListenerPromociones(ActionListener promociones);
    public void showMessage(String message);
    public void cleanFieldsPromoTemp();
    public void cleanFieldsPromoProd();
    public void actualizaPromociones(ArrayList<Promocion> promociones);
    public Promocion getSelectedPromocion();
    public Producto getSelectedProducto();
    public boolean isDosPorUno();
    public boolean isDtoPorCant();
    public int getCantMin();
    public double getPrecioUnit();
    public String getDiasProducto();
    public String getDiasTemporal();
    public String getFormaDePago();
    public int getDto();
    public boolean isAcumulable();
    public String getNombrePromocion();
}
