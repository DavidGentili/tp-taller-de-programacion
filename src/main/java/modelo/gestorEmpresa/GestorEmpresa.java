
package modelo.gestorEmpresa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import enums.EstadoMozos;
import enums.FormasDePago;
import exceptions.*;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.*;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.OperarioNoEncontradoException;
import exceptions.operarios.OperarioYaExistenteException;
import exceptions.operarios.UsuarioNoAutorizadoException;
import exceptions.operarios.UsuarioNoLogueadoException;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;
import exceptions.promociones.PromocionNoEncontradaException;
import exceptions.promociones.PromocionYaExistenteException;
import modelo.archivo.Archivo;
import modelo.configEmpresa.*;

public class GestorEmpresa {
    private static GestorEmpresa instance = null;
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private ArrayList<Comanda> comandas;
    private ArrayList<MozoMesa> asignacionMozosMesas;
    private GestorDePromociones promociones;
    private StateGestorEmpresa state;

    private GestorEmpresa(){
        comandas = new ArrayList<>();
        configuracion = ConfiguracionEmpresa.getInstance();
        asignacionMozosMesas = new ArrayList<>();
        promociones = new GestorDePromociones();
        state = new StateClose(this);
    }

    public static GestorEmpresa getInstance(){
        if(instance == null)
            instance = new GestorEmpresa();
        return instance;
    }

    //METODOS ESTADOS

    public void abrirEmpresa() throws NoHayMozosAsignadosException, CantidadMinimaDeProductosException,
            CantidadMinimaDeProductosEnPromocionException, CantidadMaximaDeMozosActivosException,
            CantidadMaximaDeMozosSuperadaException, EmpresaAbiertaException, CantidadMaximaDeMozosDeFrancoException, HayMozoSinEstadoAsignadoException {
        state.abrirEmpresa();
    }

    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException {
        state.cerrarEmpresa();
    }

    protected StateGestorEmpresa getState(){
        return state;
    }

    /**
     * Define un estado para la empresa
     * pre: state != null
     * @param state : Nuevo estado de la empresa
     */
    protected void setState(StateGestorEmpresa state){
        assert state != null : "El nuevo estado no puede ser nulo";
        this.state = state;
    }

    //METODOS ASIGNACION MOZO MESA
    
    /**
     * Se encarga de asignarle un Mozo a una mesa un determinado dia, y guarda esta asignacion en la coleccion asignacionMozosMesas
     * @param mozoId : el mozo al que se le asignara la mesa
     * @param nroMesa : la mesa que se le asignara al mozo
     * @param fecha : dia especifico en que va a ocurrir esta asignacion
     * pre: mozo debe ser distinto de null
     * post: se añadira una nueva asignacion a la coleccion 
     */
    public void asignarMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, MesaYaOcupadaException, EmpresaAbiertaException {
    	state.asignarMozo(mozoId, nroMesa, fecha);
    }

    public ArrayList<MozoMesa> getAsignacionMozosMesas(){
        return asignacionMozosMesas;
    }

    protected void setAsignacionMozosMesas(ArrayList<MozoMesa> asignacionMozosMesas){
        assert asignacionMozosMesas != null : "Las asignaciones no pueden ser nulas";
        this.asignacionMozosMesas = asignacionMozosMesas;
    }

    public ArrayList<MozoMesa> getMozoMeza(){
        return asignacionMozosMesas;
    }

    protected MozoMesa getMozoMezaByNroMesa(int nroMesa){
        assert nroMesa >= 0 : "El numero mesa debe ser positivo";
        MozoMesa res = null;
        int i = 0;
        while(i < asignacionMozosMesas.size() && res == null){
            if(asignacionMozosMesas.get(i).getMesa().getNroMesa() == nroMesa)
                res = asignacionMozosMesas.get(i);
            i++;
        }
        return res;
    }

    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException, MesaNoAsignadaException {
        state.eliminarRelacionMozoMesa(nroMesa);
    }

    //METODOS COMANDAS

    public ArrayList<Comanda> getComandas(){
        return comandas;
    }

    protected Comanda getComandaByNroMesa(int nroMesa){
        Comanda res = null;
        int i = 0;
        while (i < comandas.size() && res == null){
            if(comandas.get(i).getMesa().getNroMesa() == nroMesa)
                res = comandas.get(i);
            i++;
        }
        return res;
    }

    protected void setComandas(ArrayList<Comanda> comandas){
        assert comandas != null : "Las comandas no pueden ser nulas";
        this.comandas = comandas;
    }

    /**
     * Se encarga de generar una nueva comanda y agregarla a la coleccion de Comandas
     * @param nroMesa : la mesa a la cual se le asignaran los distinos pedidos
     * @throws MesaYaOcupadaException : si la mesa ya esta ocupada con otra comanda
     * pre : mesa != null
     * post : se añadira una nueva comanda a la coleccion 
     * post : la mesa pasa a estado ocupado
     */
    public void agregarComanda(int nroMesa, Comanda comanda) throws MesaYaOcupadaException, EmpresaCerradaException {
    	state.agregaComanda(nroMesa);
    }

    /**
     * Se encarga de desactivar una comanda activa
     * @param nroMesa : comanda que se quiere cerrar
     * @throws MesaYaLiberadaException : si la mesa ya fue liberada
     * pre : comanda != null
     * post : la comanda pasara a estado "Cerrada"
     * post : la mesa asociada a la comanda pasara de estado "ocupada" a estado "libre"
     * post : facturacion de la comanda , verifica si cumple con alguna promocion activa
     */
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws MesaYaLiberadaException, EmpresaCerradaException, ComandaYaCerradaException, MesaNoEncontradaException {
        state.cerrarComanda(nroMesa, formaDePago);
    }

    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException, ComandaYaCerradaException, ComandaNoEncontradaException {
        state.agregarPedido(nroMesa, pedido);
    }

    //METODOS PROMOCION

    /**
     * Retorna las promociones almacenadas
     * @return Promociones almacenadas
     */
    public ArrayList<Promocion> getPromociones(){
        return promociones.getPromociones();
    }

    /**
     * Retorna las promociones de producto almacenadas
     * @return promociones de producto
     */
    public ArrayList<PromocionProducto> getPromocionesProducto(){
        return promociones.getPromocionProducto();
    }

    protected void setPromoProduct(ArrayList<PromocionProducto> promoProduct) {
        promociones.setPromoProduct(promoProduct);
    }

    /**
     * Retorna las promociones temporales almacenadas
     * @return promociones temprales
     */
    public ArrayList<PromocionTemp> getPromocionesTemporales(){
        return promociones.getPromocionTemporales();
    }

    protected void setPromoTemp(ArrayList<PromocionTemp> promoTemp) {
        promociones.setPromoTemp(promoTemp);
    }

    /**
     * Agrega una promocion de producto a la coleccion
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";
        promociones.agregarPromocionProducto(promo);
    }

    /**
     * Agrega una promocion temporal a la coleccion
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";
        promociones.agregarPromocionTemp(promo);
    }

    /**
     * Elimina una promocion del registro de promociones
     * @param id : El id de la promocion buscada
     * @throws IdIncorrectoException : Si el id es incorrecto
     */
    public void eliminarPromocion(int id) throws IdIncorrectoException, PromocionNoEncontradaException {
        if(id < 0 )
            throw new IdIncorrectoException();
        promociones.eliminarPromocion(id);
    }

    /**
     * Se encarga de activar una promocion desactivada
     * @param id : id de la promocion que se desea activar
     * @throws IdIncorrectoException : Si el id ingresado es uncorrecto
     * post : el estado de la promocion asociada al idpromo pasa a activa (activa=true)
     */
    public void activarPromocion(int id) throws IdIncorrectoException, PromocionNoEncontradaException {
        if(id < 0)
            throw new IdIncorrectoException();
        promociones.activarPromocion(id);
    }


    /**
     * Se encarga de desactivar una promocion activa
     * @param id : id de la promocion que se desea desactivar
     * @throws IdIncorrectoException : Si el id ingresado es incorrecto
     * post : el estado de la promocion asociada al idpromo pasa a desactivada (activa=false)
     */
    public void desactivarpromocion(int id) throws IdIncorrectoException, PromocionNoEncontradaException {
        if(id < 0)
            throw new IdIncorrectoException();
        promociones.desactivarPromocion(id);
    }

    ///METODOS VERIFICACION PARA CONFIGURACION

    //METODOS MOZOS
    public boolean puedeAgregarMozo() throws EmpresaAbiertaException{
        return state.puedeAgregarMozo();
    }

    public boolean puedeDefinirEstadoMozo() throws EmpresaAbiertaException{
        return state.puedeDefinirEstadoMozo();
    }

    public boolean puedeEliminarMozo(int idMozo) throws MozoNoEncontradoException, EmpresaAbiertaException{
        return state.puedeEliminarMozo(idMozo);
    }

    //METODOS MESAS
    public boolean puedeEliminarMesa(int nroMesa) throws MesaNoEncontradaException, MesaYaOcupadaException{
        return state.puedeEliminarMesa(nroMesa);
    }

    //METODOS PRODUCTOS
    public boolean puedeEliminarProducto(int idProducto) throws ProductoNoEncontradoException, ProductoEnPedidoException{
        return state.puedeEliminarProducto(idProducto);
    }

    //PERSITENCIA

    public void recuperarEmpresa() throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException {
        try{
            GestorEmpresaDTO pers = new GestorEmpresaDTO();
            pers.recuperarDatos();
            promociones.setPromoTemp(pers.getPromocionTemporales());
            promociones.setPromoProduct(pers.getPromocionesProducto());
            comandas = pers.getComandas();
            asignacionMozosMesas = pers.getAsignacionMozosMesas();
            state = pers.getInstanceState(this);
            Promocion.setNroPromociones(pers.getNroPromocion());
        } catch(ArchivoNoInciliazadoException | IOException | ClassNotFoundException e){

        }

    }

    public void guardarEmpresa() throws ArchivoNoInciliazadoException, IOException {
        GestorEmpresaDTO persitencia = new GestorEmpresaDTO(this);
        persitencia.almacenarDatos();
    }

}
