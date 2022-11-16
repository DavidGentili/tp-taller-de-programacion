package vista;

import controlador.Commands;
import enums.EstadoMozos;
import enums.FormasDePago;
import modelo.archivo.Asistencia;
import modelo.archivo.Factura;
import modelo.archivo.VentasMesa;
import modelo.archivo.VentasMozo;
import modelo.configEmpresa.*;
import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.MozoMesa;
import modelo.gestorEmpresa.Pedido;
import modelo.gestorEmpresa.Promocion;
import vista.interfaces.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaEmpresa extends JFrame implements IVista, IVMesas, IVMozos, IVAsignaciones,
        IVProductos, IVPromociones, IVArchivo, IVOperarios, IVConfiguracion, IVComanda {
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
    private JList<VentasMesa> listVentasMesas;
    private DefaultListModel<VentasMesa> listaVentasMesaModel;
    private JList<VentasMozo> listVentasMozos;
    private DefaultListModel<VentasMozo> listaVentasMozoModel;
    private JPanel panelDatos;
    private JTextField fieldMayorVentas;
    private JTextField fieldMenorVentas;
    private JButton btnEstadisticas;
    private JList<Asistencia> listAsistencia;
    private DefaultListModel<Asistencia> listaAsitenciaModel;
    private JList<Factura> listFacturas;
    private JComboBox<String> comboBoxEstadoMozo;
    private JButton btnDefinirEstadoMozo;
    private JPanel panelOperarios;
    private JList<Operario> listOperarios;
    private DefaultListModel<Operario> listaOperariosModel;
    private JPanel panelFormOperarios;
    private JTextField fieldNombreApellidoOperario;
    private JTextField fieldNombreUsuarioOperario;
    private JPasswordField fieldContraseniOperario;
    private JButton btnEliminarOperario;
    private JButton btnAgregarOperario;
    private JPanel panelCambiarContrasenia;
    private JPanel panelConfigGeneral;
    private JPasswordField fieldContraseniaActualCambiarContrasenia;
    private JPasswordField fieldContraseniaNuevaCambiarContrasenia;
    private JButton btnCambiarContrasenia;
    private JLabel lblUsuarioNoAutorizado;
    private JTextField fieldNombreEmpresa;
    private JTextField fieldSueldoBasico;
    private JTextField fieldBonificacion;
    private JLabel lblNombreEmpresa;
    private JLabel lblSueldoEmpresa;
    private JButton btnCambiarNombreLocal;
    private JButton btnCambiarSueldo;
    private JButton btnCerrarSesion;
    private JList<Comanda> listComandas;
    private DefaultListModel<Comanda> listaComandasModel;
    private JList<Pedido> listPedidos;
    private DefaultListModel<Pedido> listaPedidosModel;
    private JTextField fieldNroProducto;
    private JTextField fieldCantidad;
    private JButton btnCerrarComanda;
    private JButton btnAgregarPedido;
    private JTextField fieldNroMesaComanda;
    private JComboBox comboBoxFormaDePagoComanda;
    private JButton btnAbrirEmpresa;
    private JButton btnCerrarEmpresa;
    private DefaultListModel<Factura> listaFacturasModel;

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
        this.btnDefinirEstadoMozo.setActionCommand(Commands.DEFINIR_ESTADO_MOZO);
        this.btnAgregarMozo.addActionListener(a);
        this.btnEliminarMozo.addActionListener(a);
        this.btnDefinirEstadoMozo.addActionListener(a);
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
    public void setActionListenerOperarios(ActionListener a) {
        this.btnAgregarOperario.setActionCommand(Commands.AGREGAR_OPERARIO);
        this.btnEliminarOperario.setActionCommand(Commands.ELIMINAR_OPERARIO);
        this.btnCambiarContrasenia.setActionCommand(Commands.CAMBIAR_CONTRASENIA);
        this.btnCerrarSesion.setActionCommand(Commands.LOGOUT);
        this.btnCerrarSesion.addActionListener(a);
        this.btnAgregarOperario.addActionListener(a);
        this.btnEliminarOperario.addActionListener(a);
        this.btnCambiarContrasenia.addActionListener(a);
    }

    @Override
    public void clearFieldsOperario() {
        this.fieldContraseniOperario.setText("");
        this.fieldNombreApellidoOperario.setText("");
        this.fieldNombreUsuarioOperario.setText("");
        this.fieldContraseniaActualCambiarContrasenia.setText("");
        this.fieldContraseniaNuevaCambiarContrasenia.setText("");
    }

    @Override
    public void actualizaOperarios(ArrayList<Operario> operarios) {
        this.listaOperariosModel.clear();
        this.listaOperariosModel.addAll(operarios);
    }

    @Override
    public String getNombreApellidoOperario() {
        return this.fieldNombreApellidoOperario.getText();
    }

    @Override
    public String getNombreUsuarioOperario() {
        return this.fieldNombreUsuarioOperario.getText();
    }

    @Override
    public String getPasswordNuevoOperario() {
        return new String(this.fieldContraseniOperario.getPassword());
    }

    @Override
    public void setActionListenerComandas(ActionListener a) {
        this.btnAgregarPedido.setActionCommand(Commands.AGREGAR_PEDIDO);
        this.btnCerrarComanda.setActionCommand(Commands.CERRAR_COMANDA);
        this.btnAbrirEmpresa.setActionCommand(Commands.ABRIR_LOCAL);
        this.btnCerrarEmpresa.setActionCommand(Commands.CERRAR_LOCAL);
        this.btnAgregarPedido.addActionListener(a);
        this.btnCerrarComanda.addActionListener(a);
        this.btnAbrirEmpresa.addActionListener(a);
        this.btnCerrarEmpresa.addActionListener(a);
    }

    public void setSelectionListener(ListSelectionListener l) {
        this.listComandas.addListSelectionListener(l);
    }

    @Override
    public int getNroMesaComanda() {
        try {
            return Integer.parseInt(fieldNroMesaComanda.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public int getNroProductoComanda() {
        try {
            return Integer.parseInt(fieldNroProducto.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public int getCantidadComanda() {
        try {
            return Integer.parseInt(fieldCantidad.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public Comanda getSelectedComanda() {
        return this.listComandas.getSelectedValue();
    }

    @Override
    public String getFormaDePagoComanda() {
        return (String) this.comboBoxFormaDePagoComanda.getSelectedItem();
    }

    @Override
    public void clearFieldComanda() {
        this.fieldCantidad.setText("");
        this.fieldNroMesaComanda.setText("");
        this.fieldNroProducto.setText("");
    }

    @Override
    public void actualizaComandas(ArrayList<Comanda> comandas) {
        listaComandasModel.clear();
        listaComandasModel.addAll(comandas);
    }

    @Override
    public void actuliazaPedido(ArrayList<Pedido> pedidos) {
        listaPedidosModel.clear();
        listaPedidosModel.addAll(pedidos);
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public String getPasswordCambiaContrasenia() {
        return new String(this.fieldContraseniaActualCambiarContrasenia.getPassword());
    }

    @Override
    public String getNuevoPasswordCambiaContrasenia() {
        return new String(this.fieldContraseniaNuevaCambiarContrasenia.getPassword());
    }

    @Override
    public void showMessageNotAutorizedOperarios(String message) {
        this.lblUsuarioNoAutorizado.setText(message);
    }

    @Override
    public Operario getSelectedOperario() {
        return this.listOperarios.getSelectedValue();
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

    @Override
    public String getEstadoMozo() {
        return (String) this.comboBoxEstadoMozo.getSelectedItem();
    }

    private void createUIComponents() {
        this.comboBoxFormasDePagoPromocion = new JComboBox<>();
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.EFECTIVO.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.MERCADOPAGO.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.TARJETA.toString());
        this.comboBoxFormasDePagoPromocion.addItem(FormasDePago.CTADNI.toString());

        this.comboBoxFormaDePagoComanda = new JComboBox<>();
        this.comboBoxFormaDePagoComanda.addItem(FormasDePago.EFECTIVO.toString());
        this.comboBoxFormaDePagoComanda.addItem(FormasDePago.MERCADOPAGO.toString());
        this.comboBoxFormaDePagoComanda.addItem(FormasDePago.TARJETA.toString());
        this.comboBoxFormaDePagoComanda.addItem(FormasDePago.CTADNI.toString());

        this.comboBoxEstadoMozo = new JComboBox<>();
        this.comboBoxEstadoMozo.addItem(EstadoMozos.ACTIVO.toString());
        this.comboBoxEstadoMozo.addItem(EstadoMozos.AUSENTE.toString());
        this.comboBoxEstadoMozo.addItem(EstadoMozos.DE_FRANCO.toString());

        //Lista Mesas
        this.listaMesasModel = new DefaultListModel<>();
        this.listMesas = new JList<>();
        listMesas.setModel(listaMesasModel);

        //Lista Mozos
        this.listaMozosModel = new DefaultListModel<>();
        this.listMozos = new JList<>();
        listMozos.setModel(listaMozosModel);

        //Lista asignaciones mozo mesa
        this.listaAsignacionesMozoMesaModel = new DefaultListModel<>();
        this.listAsignacionesMozoMesa = new JList<>();
        listAsignacionesMozoMesa.setModel(listaAsignacionesMozoMesaModel);

        //Lista Productos
        this.listaProductosDefault = new DefaultListModel<>();
        this.listProductos = new JList<>();
        listProductos.setModel(listaProductosDefault);

        //Lista Promociones
        this.listaPromocionesModel = new DefaultListModel<>();
        this.listPromociones = new JList<>();
        listPromociones.setModel(listaPromocionesModel);

        //Lista VentasMozo
        this.listaVentasMozoModel = new DefaultListModel<>();
        this.listVentasMozos = new JList<>();
        listVentasMozos.setModel(listaVentasMozoModel);

        //Lista VentasMesa
        this.listaVentasMesaModel = new DefaultListModel<>();
        this.listVentasMesas = new JList<>();
        listVentasMesas.setModel(listaVentasMesaModel);

        //Lista Asistencia
        this.listaAsitenciaModel = new DefaultListModel<>();
        this.listAsistencia = new JList<>();
        listAsistencia.setModel(listaAsitenciaModel);

        //Lista Facturas
        this.listaFacturasModel = new DefaultListModel<>();
        this.listFacturas = new JList<>();
        listFacturas.setModel(listaFacturasModel);

        //Lista Operarios
        this.listaOperariosModel = new DefaultListModel<>();
        this.listOperarios = new JList<>();
        listOperarios.setModel(listaOperariosModel);

        //Lista Comandas
        this.listaComandasModel = new DefaultListModel<>();
        this.listComandas = new JList<>();
        listComandas.setModel(listaComandasModel);

        //Lista Pedidos
        this.listaPedidosModel = new DefaultListModel<>();
        this.listPedidos = new JList<>();
        listPedidos.setModel(listaPedidosModel);
    }

    @Override
    public void setActionListenerEstadisticas(ActionListener a) {
        this.btnEstadisticas.setActionCommand(Commands.CALCULAR_ESTADISTICAS);
        this.btnEstadisticas.addActionListener(a);
    }

    @Override
    public void setMozoMayorVolumenVentas(VentasMozo mozo) {
        this.fieldMayorVentas.setText(mozo != null ? mozo.toString() : "No hay registro");
    }

    @Override
    public void setMozoMenorVolumenVentas(VentasMozo mozo) {
        this.fieldMenorVentas.setText(mozo != null ? mozo.toString() : "No hay registro");
    }

    @Override
    public void setEstadisticasMozos(ArrayList<VentasMozo> mozos) {
        listaVentasMozoModel.clear();
        listaVentasMozoModel.addAll(mozos);
    }

    @Override
    public void setPromedioMesas(ArrayList<VentasMesa> mesas) {
        listaVentasMesaModel.clear();
        listaVentasMesaModel.addAll(mesas);
    }

    @Override
    public void cargaAsistencia(ArrayList<Asistencia> asistencias) {
        listaAsitenciaModel.clear();
        listaAsitenciaModel.addAll(asistencias);
    }

    @Override
    public void actualizaFacturas(ArrayList<Factura> facturas) {
        listaFacturasModel.clear();
        listaFacturasModel.addAll(facturas);
    }

    @Override
    public void setActionListenerConfiguracion(ActionListener a) {
        this.btnCambiarNombreLocal.setActionCommand(Commands.CAMBIAR_NOMBRE_LOCAL);
        this.btnCambiarSueldo.setActionCommand(Commands.CAMBIAR_SUELDO);
        this.btnCambiarNombreLocal.addActionListener(a);
        this.btnCambiarSueldo.addActionListener(a);
    }

    @Override
    public String getNombreLocal() {
        return this.fieldNombreEmpresa.getText();
    }

    @Override
    public double getBasico() {
        try {
            return Double.parseDouble(fieldSueldoBasico.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public double getBonificacion() {
        try {
            return Double.parseDouble(fieldBonificacion.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public void clearFieldsConfiguracion() {
        this.fieldNombreEmpresa.setText("");
        this.fieldSueldoBasico.setText("");
        this.fieldBonificacion.setText("");
    }

    @Override
    public void setNombreEmpresa(String name) {
        this.lblNombreEmpresa.setText(name);
    }

    @Override
    public void setSueldo(Sueldo sueldo) {
        if(sueldo == null)
            lblSueldoEmpresa.setText("El sueldo no se encuentra disponible");
        else
            lblSueldoEmpresa.setText(String.format("Sueldo basico : $-%8.1f - Bonificacion : %4.1f%%", sueldo.getBasico(), sueldo.getBonificacionPorHijo()));
    }
}
