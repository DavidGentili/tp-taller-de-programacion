package vista;

import controlador.Commands;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.MozoMesa;
import vista.interfaces.IVAsignaciones;
import vista.interfaces.IVMesas;
import vista.interfaces.IVMozos;
import vista.interfaces.IVista;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaEmpresa extends JFrame implements IVista, IVMesas, IVMozos, IVAsignaciones {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel tabMozoMesa;
    private JPanel tabArchivo;
    private JPanel tabConfig;
    private JPanel tabComandas;
    private JPanel tabProductos;
    private JPanel panelMesas;
    private JPanel panelMozos;
    private JPanel panelAsignaciones;
    private JList<Mesa> listMesas;
    private DefaultListModel<Mesa> listaMesasModel;
    private JTextField fieldNroMesa;
    private JButton btnAgregarMesa;
    private JTextField fieldCantSillas;
    private JLabel lblNroMesa;
    private JLabel lblCantSillas;
    private JButton btnEliminarMesa;
    private JList<Mozo> listMozos;
    private DefaultListModel<Mozo> listaMozosModel;
    private JTextField fieldNombreYApellidoMozo;
    private JTextField fieldNacimientoMozo;
    private JTextField fieldCantHijosMozo;
    private JLabel lblNombreYApellidoMozo;
    private JLabel lblNacimientoMozo;
    private JLabel lblCantHijosMozo;
    private JButton btnEliminarMozo;
    private JButton btnAgregarMozo;
    private JList<MozoMesa> listAsignacionesMozoMesa;
    private DefaultListModel<MozoMesa> listaAsignacionesMozoMesaModel;
    private JButton btnEliminarAsignacion;
    private JButton btnAgregarAsignacion;

    public VentanaEmpresa(){
        setTitle("Restaurante");
        setContentPane(this.mainPanel);
        setVisible(true);
        this.pack();
        this.setSize(1024,512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void setActionListenerMesas(ActionListener e) {
        this.btnAgregarMesa.setActionCommand(Commands.AGREGAR_MESA);
        this.btnEliminarMesa.setActionCommand(Commands.ELIMINAR_MESA);
        this.btnAgregarMesa.addActionListener(e);
        this.btnEliminarMesa.addActionListener(e);
    }

    @Override
    public int getNroMesa() {
        try{
            return Integer.parseInt(this.fieldNroMesa.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public int getCantSillas() {
        try{
            return Integer.parseInt(this.fieldCantSillas.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public void actualizaMesas(ArrayList<Mesa> mesas) {
        listaMesasModel.clear();
        for(Mesa mesa : mesas)
            this.listaMesasModel.addElement(mesa);
    }

    @Override
    public void setActionListenerAsignacionesMozoMesa(ActionListener a) {
        this.btnAgregarAsignacion.setActionCommand(Commands.AGREGAR_ASIGNACION);
        this.btnEliminarAsignacion.setActionCommand(Commands.ELIMINAR_ASIGNACION);
        this.btnAgregarAsignacion.addActionListener(a);
        this.btnEliminarAsignacion.addActionListener(a);
    }

    @Override
    public Mesa getSelectedMesa() {
        return (Mesa) this.listMesas.getSelectedValue();
    }

    @Override
    public void setActionListenerMozo(ActionListener a) {
        this.btnAgregarMozo.setActionCommand(Commands.AGREGAR_MOZO);
        this.btnEliminarMozo.setActionCommand(Commands.ELIMINAR_MOZO);
        this.btnAgregarMozo.addActionListener(a);
        this.btnEliminarMozo.addActionListener(a);
    }

    @Override
    public void actualizarMozos(ArrayList<Mozo> mozos) {
        this.listaMozosModel.clear();
        this.listaMozosModel.addAll(mozos);
        this.repaint();
    }

    @Override
    public String getNombreApellidoMozo() {
        return this.fieldNombreYApellidoMozo.getText();
    }

    @Override
    public String getNacimientoMozo() {
        return this.fieldNacimientoMozo.getText();
    }

    @Override
    public int getCantHijosMozo() {
        try{
            return Integer.parseInt(this.fieldCantHijosMozo.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public Mozo getSelectedMozo() {
        return this.listMozos.getSelectedValue();
    }

    @Override
    public MozoMesa getSelectedAsignacionMozoMesa() {
        return listAsignacionesMozoMesa.getSelectedValue();
    }

    @Override
    public void actualizarAsignacionesMozoMesa(ArrayList<MozoMesa> asignaciones) {
        this.listaAsignacionesMozoMesaModel.clear();
        this.listaAsignacionesMozoMesaModel.addAll(asignaciones);

    }

    @Override
    public void clearFieldsMesas(){
        this.fieldCantSillas.setText("");
        this.fieldNroMesa.setText("");
    }

    @Override
    public void clearFieldsMozo(){
        this.fieldNacimientoMozo.setText("");
        this.fieldNombreYApellidoMozo.setText("");
        this.fieldCantHijosMozo.setText("");
    }

    private void createUIComponents() {
        //Lista Mesas
        this.listaMesasModel = new DefaultListModel<>();
        this.listMesas = new JList<>();
        listMesas.setModel(listaMesasModel);

        //Lista Mozos
        this.listaMozosModel = new DefaultListModel();
        this.listMozos = new JList<>();
        listMozos.setModel(listaMozosModel);

        //Lista asignaciones mozo mesa
        this.listaAsignacionesMozoMesaModel = new DefaultListModel();
        this.listAsignacionesMozoMesa = new JList<>();
        listAsignacionesMozoMesa.setModel(listaAsignacionesMozoMesaModel);


    }
}
