package vista.interfaces;

import modelo.configEmpresa.Producto;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVProductos {
    public void setActionListenerProducto(ActionListener a);
    public void actualizarProductos(ArrayList<Producto> productos);
    public Producto getSelectedProducto();
    public String getNombreProducto();
    public double getPrecioCosto();
    public double getPrecioVenta();
    public int getStock();
    public void clearFieldsProductos();
    public void showMessage(String message);
}
