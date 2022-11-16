package controlador;

import enums.FormasDePago;
import exceptions.IdIncorrectoException;
import exceptions.controlador.ErrorAlCrearProductoException;
import exceptions.controlador.ErrorAlEliminarProductoException;
import exceptions.controlador.ErrorPromocionException;
import exceptions.operarios.NoSeCambioContraseniaException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import exceptions.operarios.UsuarioNoLogueadoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;
import exceptions.promociones.PromocionNoEncontradaException;
import exceptions.promociones.PromocionYaExistenteException;
import helpers.HelpersProducto;
import helpers.PromocionHelpers;
import modelo.Empresa;
import modelo.configEmpresa.Producto;
import modelo.gestorEmpresa.Promocion;
import modelo.gestorEmpresa.PromocionProducto;
import modelo.gestorEmpresa.PromocionTemp;
import vista.interfaces.IVProductos;
import vista.interfaces.IVPromociones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ControladorProductos implements Observer, ActionListener {

    private IVProductos vProductos;
    private IVPromociones vPromociones;

    public ControladorProductos(IVProductos vistaProductos, IVPromociones vistaPromociones){
        this.vProductos = vistaProductos;
        this.vPromociones = vistaPromociones;
        Empresa.getInstance().addObserver(this);
        vProductos.setActionListenerProducto(this);
        vPromociones.setActionListenerPromociones(this);
        actualizarProductos();
        actualizarPromociones();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try{
            if(command.equals(Commands.AGREGAR_PRODUCTO))
                agregaProducto();
            if(command.equals(Commands.ELIMINAR_PRODUCTO))
                eliminaProducto();
            if(command.equals(Commands.AGREGAR_PROMOCION_PRODUCTO))
                agregarPromocionProducto();
            if(command.equals(Commands.AGREGAR_PROMOCION_TEMPORAL))
                agregarPromocionTemporal();
            if(command.equals(Commands.ELIMINAR_PROMOCION))
                eliminarPromocion();
            if(command.equals(Commands.DESACTIVAR_PROMOCION))
                desactivarPromocion();
            if(command.equals(Commands.ACTIVAR_PROMOCION))
                activarPromocion();
        }catch (ProductoYaExistenteException | UsuarioNoLogueadoException | ErrorAlCrearProductoException |
                UsuarioNoAutorizadoException | NoSeCambioContraseniaException | ProductoNoEncontradoException |
                IdIncorrectoException | ProductoEnPedidoException | ErrorAlEliminarProductoException |
                PromocionNoEncontradaException | PromocionYaExistenteException | ErrorPromocionException ex){
            System.out.println(ex);
            vProductos.showMessage(ex.getMessage());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        actualizarProductos();
        actualizarPromociones();
    }

    private void agregaProducto() throws NoSeCambioContraseniaException, ProductoYaExistenteException, UsuarioNoLogueadoException, UsuarioNoAutorizadoException, ErrorAlCrearProductoException {
        String nombre = vProductos.getNombreProducto();
        double precioCosto = vProductos.getPrecioCosto();
        double precioVenta = vProductos.getPrecioVenta();
        int stock = vProductos.getStock();
        HelpersProducto.checkNombreProducto(nombre);
        HelpersProducto.checkPrecios(precioCosto,precioVenta);
        Producto prod = new Producto(nombre, precioCosto, precioVenta, stock);
        Empresa.getInstance().agregarProducto(prod);
        vProductos.clearFieldsProductos();
    }

    private void eliminaProducto() throws ErrorAlEliminarProductoException, NoSeCambioContraseniaException, ProductoNoEncontradoException, UsuarioNoLogueadoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException {
        Producto prod = vProductos.getSelectedProducto();
        if(prod == null)
            throw new ErrorAlEliminarProductoException("Debe seleccionar un producto de la lista");
        Empresa.getInstance().eliminarProducto(prod.getId());

    }

    private void agregarPromocionProducto() throws ErrorPromocionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, PromocionYaExistenteException {
        boolean dosPorUno = vPromociones.isDosPorUno();
        boolean descPorCant = vPromociones.isDtoPorCant();

        if(!dosPorUno && !descPorCant)
            throw new ErrorPromocionException("Debe seleccionar un tipo de promocion");

        if(dosPorUno)
            agregaPromocionDosPorUno();
        else
            agregaPromocionDescPorCant();
        vPromociones.cleanFieldsPromoProd();

    }

    private void agregaPromocionDosPorUno() throws ErrorPromocionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, PromocionYaExistenteException {
        String dias = vPromociones.getDiasProducto();
        Producto prod = vPromociones.getSelectedProducto();

        if(prod == null)
            throw new ErrorPromocionException("Debe seleccionar un producto de la lista");
        PromocionHelpers.checkDias(dias);
        PromocionProducto promo = new PromocionProducto(dias, prod);
        Empresa.getInstance().agregarPromocionProducto(promo);
    }

    private void agregaPromocionDescPorCant() throws ErrorPromocionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, PromocionYaExistenteException {
        String dias = vPromociones.getDiasProducto();
        Producto prod = vPromociones.getSelectedProducto();
        double precio = vPromociones.getPrecioUnit();
        int cantMin = vPromociones.getCantMin();

        if(prod == null)
            throw new ErrorPromocionException("Debe seleccionar un producto de la lista");
        if(precio < 0)
            throw new ErrorPromocionException("El precio no es correcto");
        if(cantMin < 0)
            throw new ErrorPromocionException("La cantidad minima no es correcta");
        PromocionHelpers.checkDias(dias);
        PromocionProducto promo = new PromocionProducto(dias, prod, cantMin, precio);
        Empresa.getInstance().agregarPromocionProducto(promo);

    }
    private void agregarPromocionTemporal() throws ErrorPromocionException, NoSeCambioContraseniaException, UsuarioNoLogueadoException, PromocionYaExistenteException {
        String dias = vPromociones.getDiasTemporal();
        String pago = vPromociones.getFormaDePago();
        String nombre = vPromociones.getNombrePromocion();
        int dto = vPromociones.getDto();
        boolean acumulable = vPromociones.isAcumulable();

        if(nombre == null || nombre.isEmpty() || nombre.isBlank())
            throw new ErrorPromocionException("El nombre de la promocion es incorrecto");
        if(dto < 0)
            throw new ErrorPromocionException("El descuento es incorrecto");
        FormasDePago formaDePago = PromocionHelpers.getFormaDePago(pago);
        PromocionHelpers.checkDias(dias);

        PromocionTemp promo = new PromocionTemp(dias, formaDePago, dto, acumulable, nombre);

        Empresa.getInstance().agregarPromocionTemp(promo);
    }
    private void eliminarPromocion() throws NoSeCambioContraseniaException, PromocionNoEncontradaException, UsuarioNoLogueadoException, IdIncorrectoException, ErrorPromocionException {
        Promocion promo = vPromociones.getSelectedPromocion();
        if(promo == null)
            throw new ErrorPromocionException("Debe seleccionar una promocion");
        Empresa.getInstance().eliminarPromocion(promo.getId());
    }
    private void activarPromocion() throws NoSeCambioContraseniaException, PromocionNoEncontradaException, UsuarioNoLogueadoException, IdIncorrectoException, ErrorPromocionException {
        Promocion promo = vPromociones.getSelectedPromocion();
        if(promo == null)
            throw new ErrorPromocionException("Debe seleccionar una promocion");
        Empresa.getInstance().activarPromocion(promo.getId());
    }

    private void desactivarPromocion() throws NoSeCambioContraseniaException, PromocionNoEncontradaException, UsuarioNoLogueadoException, IdIncorrectoException, ErrorPromocionException {
        Promocion promo = vPromociones.getSelectedPromocion();
        if(promo == null)
            throw new ErrorPromocionException("Debe seleccionar una promocion");
        Empresa.getInstance().desactivarPromocion(promo.getId());
    }

    private void actualizarProductos(){
        try{
            vProductos.actualizarProductos(Empresa.getInstance().getProductos());
        } catch (UsuarioNoLogueadoException ignored) {
            System.out.println(ignored);
        }
    }

    private void actualizarPromociones(){
        try{
            vPromociones.actualizaPromociones(Empresa.getInstance().getPromociones());
        } catch (UsuarioNoLogueadoException ignored) {
        }
    }
}
