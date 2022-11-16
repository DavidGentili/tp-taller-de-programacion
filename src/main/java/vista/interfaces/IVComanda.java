package vista.interfaces;

import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.Pedido;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public interface IVComanda {

    public void setActionListenerComandas(ActionListener a);
    public void setSelectionListener(ListSelectionListener listener);
    public int getNroMesaComanda();
    public int getNroProductoComanda();
    public int getCantidadComanda();
    public Comanda getSelectedComanda();
    public String getFormaDePagoComanda();
    public void clearFieldComanda();
    public void actualizaComandas(ArrayList<Comanda> comandas);
    public void actuliazaPedido(ArrayList<Pedido> pedidos);
    public void showMessage(String message);
}
