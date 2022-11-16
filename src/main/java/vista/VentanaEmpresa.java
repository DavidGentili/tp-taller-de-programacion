package vista;

import controlador.Commands;
import enums.FormasDePago;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Producto;
import modelo.gestorEmpresa.MozoMesa;
import modelo.gestorEmpresa.Promocion;
import modelo.gestorEmpresa.PromocionProducto;
import modelo.gestorEmpresa.PromocionTemp;
import vista.interfaces.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaEmpresa extends JFrame implements IVista, IVMesas, IVMozos, IVAsignaciones, IVProductos, IVPromociones {
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
    private JPanel panelProductos;
    private JPanel panelPromociones;
    private JList<Producto> listProductos;
    private DefaultListModel<Producto> listaProductosDefault;
    private JTextField fieldNombreProducto;
    private JTextField fieldPrecioCosto;
    private JTextField fieldPrecioVenta;
    private JTextField fieldStock;
    private JButton btnAgregarProducto;
    private JButton btnEliminarProducto;
    private JLabel lblNombreProducto;
    private JLabel lblPrecioCosto;
    private JLabel lblPrecioVenta;
    private JLabel lblStock;
    private JPanel panelBtnsProductos;
    private JList<Promocion> listPromociones;
    private DefaultListModel<Promocion> listaPromocionesModel;
    private JTextField fieldDiasProducto;
    private JRadioButton rdBtnDosPorUno;
    private JRadioButton rdBtnDtoPorCantidad;
    private JTextField fieldCantidadMinima;
    private JTextField fieldPrecioUnitario;
    private JButton btnAgregarPromocionProducto;
    private JLabel lblDiasPromocionProducto;
    private JLabel lblTipoPromoProducto;
    private JPanel panelBtnsPromociones;
    private JPanel panelBtnPromocionProducto;
    private JPanel panelBtnsPromocionesTemporales;
    private JTextField fieldNombrePromocion;
    private JTextField fieldDiasPromocionTemp;
    private JComboBox<String> comboBoxFormasDePagoPromocion;
    private JCheckBox checkBoxAcumulable;
    private JButton btnAgregarPromocionTemp;
    private JPanel panelPromocionesGeneral;
    private JButton btnActivarPromocion;
    private JButton btnDesactivarPromocion;
    private JButton btnEliminarPromocion;
    private JTextField fieldDescuentoPromoTemp;

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
    public void setActionListenerProducto(ActionListener a) {
        this.btnAgregarProducto.setActionCommand(Commands.AGREGAR_PRODUCTO);
        this.btnEliminarProducto.setActionCommand(Commands.ELIMINAR_PRODUCTO);
        this.btnAgregarProducto.addActionListener(a);
        this.btnEliminarProducto.addActionListener(a);
    }

    @Override
    public void actualizarProductos(ArrayList<Producto> productos) {
        listaProductosDefault.clear();
        listaProductosDefault.addAll(productos);
    }

    @Override
    public Producto getSelectedProducto() {
        return listProductos.getSelectedValue();
    }

    @Override
    public boolean isDosPorUno() {
        return this.rdBtnDosPorUno.isSelected();
    }

    @Override
    public boolean isDtoPorCant() {
        return this.rdBtnDtoPorCantidad.isSelected();
    }

    @Override
    public int getCantMin() {
        try{
            return Integer.parseInt(this.fieldCantidadMinima.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public double getPrecioUnit() {
        try{
            return Integer.parseInt(this.fieldPrecioUnitario.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public String getDiasProducto() {
        return this.fieldDiasProducto.getText();
    }

    @Override
    public String getDiasTemporal() {
        return this.fieldDiasPromocionTemp.getText();
    }

    @Override
    public String getFormaDePago() {
        return this.comboBoxFormasDePagoPromocion.getSelectedItem().toString();
    }

    @Override
    public int getDto() {
        try{
            return Integer.parseInt(this.fieldDescuentoPromoTemp.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public boolean isAcumulable() {
        return this.checkBoxAcumulable.isSelected();
    }

    @Override
    public String getNombrePromocion() {
        return this.fieldNombrePromocion.getText();
    }

    @Override
    public String getNombreProducto() {
        return fieldNombreProducto.getText();
    }

    @Override
    public double getPrecioCosto() {
        try {
            return Double.parseDouble(fieldPrecioCosto.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public double getPrecioVenta() {
        try {
            return Double.parseDouble(fieldPrecioVenta.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public int getStock() {
        try {
            return Integer.parseInt(fieldStock.getText());
        }catch (NumberFormatException e){
            return 0;
        }
    }

    @Override
    public void clearFieldsProductos() {
        fieldNombreProducto.setText("");
        fieldPrecioCosto.setText("");
        fieldPrecioVenta.setText("");
        fieldStock.setText("");
    }

    @Override
    public void setActionListenerPromociones(ActionListener a) {
        this.btnAgregarPromocionProducto.setActionCommand(Commands.AGREGAR_PROMOCION_PRODUCTO);
        this.btnAgregarPromocionTemp.setActionCommand(Commands.AGREGAR_PROMOCION_TEMPORAL);
        this.btnEliminarPromocion.setActionCommand(Commands.ELIMINAR_PROMOCION);
        this.btnDesactivarPromocion.setActionCommand(Commands.DESACTIVAR_PROMOCION);
        this.btnActivarPromocion.setActionCommand(Commands.ACTIVAR_PROMOCION);
        this.btnAgregarPromocionProducto.addActionListener(a);
        this.btnAgregarPromocionTemp.addActionListener(a);
        this.btnEliminarPromocion.addActionListener(a);
        this.btnDesactivarPromocion.addActionListener(a);
        this.btnActivarPromocion.addActionListener(a);
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void cleanFieldsPromoTemp() {
        this.fieldNombrePromocion.setText("");
        this.fieldDiasPromocionTemp.setText("");
        this.fieldDescuentoPromoTemp.setText("");
        this.checkBoxAcumulable.setSelected(false);
    }

    @Override
    public void cleanFieldsPromoProd() {
        this.fieldCantidadMinima.setText("");
        this.fieldPrecioUnitario.setText("");

    }

    @Override
    public void actualizaPromociones(ArrayList<Promocion> promociones) {
        listaPromocionesModel.clear();
        listaPromocionesModel.addAll(promociones);
        this.repaint();
    }

    @Override
    public Promocion getSelectedPromocion() {
        return this.listPromociones.getSelectedValue();
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
        this.comboBoxFormasDePagoPromocion = new JComboBox<>();
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.EFECTIVO.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.MERCADOPAGO.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.TARJETA.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.CTADNI.toString());


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

        //Lista Productos
        this.listaProductosDefault = new DefaultListModel();
        this.listProductos = new JList<>();
        listProductos.setModel(listaProductosDefault);

        //Lista Promociones
        this.listaPromocionesModel = new DefaultListModel();
        this.listPromociones = new JList<>();
        listPromociones.setModel(listaPromocionesModel);

    }

}
