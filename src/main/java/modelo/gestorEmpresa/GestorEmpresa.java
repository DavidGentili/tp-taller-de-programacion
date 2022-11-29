
package modelo.gestorEmpresa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import enums.FormasDePago;
import exceptions.*;
import exceptions.comandas.ComandaNoEncontradaException;
import exceptions.comandas.ComandaYaCerradaException;
import exceptions.gestorEmpresa.*;
import exceptions.mesas.*;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import exceptions.productos.ProductoEnPedidoException;
import exceptions.productos.ProductoNoEncontradoException;
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
        invariante();
    }

    public void cerrarEmpresa() throws EmpresaCerradaException, HayComandasActivasException {
        state.cerrarEmpresa();
        invariante();
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
        invariante();
        assert this.state == state : "No se asigno correctamente el estado";
    }

    //METODOS ASIGNACION MOZO MESA
    
    /**
     * Se encarga de asignarle un Mozo a una mesa un determinado dia, y guarda esta asignacion en la coleccion asignacionMozosMesas
     * pre: idMozo >= 0
     * nroMesa >= 0
     * @param mozoId : el mozo al que se le asignara la mesa
     * @param nroMesa : la mesa que se le asignara al mozo
     * @param fecha : dia especifico en que va a ocurrir esta asignacion
     * pre: mozo debe ser distinto de null
     * post: se añadira una nueva asignacion a la coleccion 
     */
    public void asignarMozo(int mozoId, int nroMesa, GregorianCalendar fecha) throws MesaNoEncontradaException, MozoNoEncontradoException, MozoNoActivoException, MesaYaOcupadaException, EmpresaAbiertaException {
        assert mozoId >= 0 : "El id del mozo no puede ser negativo";
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        state.asignarMozo(mozoId, nroMesa, fecha);
        invariante();
    }

    /**
     * Retorna las asignaciones de mozos y mesas
     * @return asignaciones de mozos y mesas
     */
    public ArrayList<MozoMesa> getAsignacionMozosMesas(){
        return asignacionMozosMesas;
    }

    /**
     * Determina las asignaciones de mozos y mesas
     * pre: asignacionesMozoMesas != null
     * @param asignacionMozosMesas asignaciones de mozos y mesas
     */
    protected void setAsignacionMozosMesas(ArrayList<MozoMesa> asignacionMozosMesas){
        assert asignacionMozosMesas != null : "Las asignaciones no pueden ser nulas";
        this.asignacionMozosMesas = asignacionMozosMesas;
        invariante();
        assert this.asignacionMozosMesas == asignacionMozosMesas : "No se asigno correctamente la lista de asignaciones";
    }

    /**
     * Retorna una asignacion de mozo a mesa por un numero de mesa
     * @param nroMesa numero de la mesa
     * @return asignacion de mozo y mesa correspondiente
     */
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

    /**
     * elimina todos los registros de la lista de asignaciones mozo mesa
     */
    protected void limpiarAsignaciones(){
        asignacionMozosMesas.clear();
        invariante();
    }

    /**
     * Elimina una asignacion mozo mesa
     * @param nroMesa numero de mesa
     * @throws EmpresaAbiertaException : Si la empresa se encuentra abierta
     * @throws MesaNoAsignadaException : Si la mesa no se encuentra asignada a ningun mozo
     */
    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException, MesaNoAsignadaException {
        state.eliminarRelacionMozoMesa(nroMesa);
        invariante();
    }

    //METODOS COMANDAS

    /**
     * Retorna una lista de comandas
     * @return lista de comandas
     */
    public ArrayList<Comanda> getComandas(){
        return comandas;
    }

    /**
     * Retorna una comanda segun un numero de mesa, si no existe una comanda asociada a dicho numero retorna null
     * @param nroMesa Numero de la mesa
     * @return Comanda asociada al numero de la mesa ingresado
     */
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

    /**
     * Determina la lista de comandas
     * @param comandas lista de comandas
     */
    protected void setComandas(ArrayList<Comanda> comandas){
        assert comandas != null : "Las comandas no pueden ser nulas";
        this.comandas = comandas;
        invariante();
    }

    /**
     * Se encarga de generar una nueva comanda y agregarla a la coleccion de Comandas
     * @param nroMesa : la mesa a la cual se le asignaran los distinos pedidos
     * @throws MesaYaOcupadaException : si la mesa ya esta ocupada con otra comanda
     * pre : nroMesa >= 0
     * post : se añadira una nueva comanda a la coleccion 
     * post : la mesa pasa a estado ocupado
     */
    public void agregarComanda(int nroMesa) throws MesaYaOcupadaException, EmpresaCerradaException, MesaNoEncontradaException {
        assert nroMesa >= 0 : "El numero de mesa debe ser mayor a 0";
        state.agregaComanda(nroMesa);
        invariante();
    }

    /**
     * Se encarga de desactivar una comanda activa
     * @param nroMesa : comanda que se quiere cerrar
     * @throws MesaYaLiberadaException : si la mesa ya fue liberada
     * pre : nroMesa >= 0
     * post : la comanda pasara a estado "Cerrada"
     * post : la mesa asociada a la comanda pasara de estado "ocupada" a estado "libre"
     * post : facturacion de la comanda , verifica si cumple con alguna promocion activa
     */
    public void cerrarComanda(int nroMesa, FormasDePago formaDePago) throws MesaYaLiberadaException, EmpresaCerradaException, ComandaYaCerradaException, MesaNoEncontradaException {
        assert nroMesa >= 0 : "El numero de mesa no puede ser negativo";
        state.cerrarComanda(nroMesa, formaDePago);
        invariante();
    }

    /**
     * Agrega un pedido a una comanda segun un numero de mesa
     * pre: nroMesa >= 0
     *      pedido != null
     * @param nroMesa numero de la mesa
     * @param pedido pedido a agregar
     * @throws EmpresaCerradaException si la empresa se encuentra cerrada
     * @throws ComandaYaCerradaException Si la comanda ya se encuentra cerrada
     * @throws ComandaNoEncontradaException Si no se encuentra la comanda
     */
    public void agregarPedido(int nroMesa, Pedido pedido) throws EmpresaCerradaException, ComandaYaCerradaException, ComandaNoEncontradaException {
        assert pedido != null : "El pedido no puede ser nulo";
        assert nroMesa >= 0 : "El nro de meso no puede ser nulo";
        state.agregarPedido(nroMesa, pedido);
        invariante();
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

    /**
     * Determina la lista de promociones de producto
     * pre: promoProduct != null
     * @param promoProduct lista de promociones de producto
     */
    protected void setPromoProduct(ArrayList<PromocionProducto> promoProduct) {
        assert promoProduct != null : "No se puede asignar una lista de promociones nula";
        promociones.setPromoProduct(promoProduct);
        invariante();
    }

    /**
     * Retorna las promociones temporales almacenadas
     * @return promociones temprales
     */
    public ArrayList<PromocionTemp> getPromocionesTemporales(){
        return promociones.getPromocionTemporales();
    }

    /**
     * Determina las promociones temporales
     * pre: promoTemp != null
     * @param promoTemp promociones temporales
     */
    protected void setPromoTemp(ArrayList<PromocionTemp> promoTemp) {
        assert promoTemp != null : "No se puede asignar una lista de promociones nula";
        promociones.setPromoTemp(promoTemp);
        invariante();
    }

    /**
     * Agrega una promocion de producto a la coleccion
     * pre: promo != null
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    public void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";
        promociones.agregarPromocionProducto(promo);
        invariante();
    }

    /**
     * Agrega una promocion temporal a la coleccion
     * pre: promo != null
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    public void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";
        promociones.agregarPromocionTemp(promo);
        invariante();
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
        invariante();
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
        invariante();
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
        invariante();
    }

    ///METODOS VERIFICACION PARA CONFIGURACION

    //METODOS MOZOS

    /**
     * Retorna si el sistema puede agregar un mozo
     * @return puede agregar un mozo
     * @throws EmpresaAbiertaException : Si la empresa se encuentra abierta
     */
    public boolean puedeAgregarMozo() throws EmpresaAbiertaException{
        return state.puedeAgregarMozo();
    }

    /**
     * Retorna si el sistema puede definir el estado de un mozo
     * @return : puede definir el estado de unmozo
     * @throws EmpresaAbiertaException Si la empresa ya se encuentra abierta
     */
    public boolean puedeDefinirEstadoMozo() throws EmpresaAbiertaException{
        return state.puedeDefinirEstadoMozo();
    }

    /**
     * Retorna si el sistema puede eliminar un mozo
     * @param idMozo id del mozo que se quiere eliminar
     * @return : Sis se puede eliminar el mozo
     * @throws MozoNoEncontradoException : No se encuentra el mozo correspondiente a ese id
     * @throws EmpresaAbiertaException : La empresa se encuentra abierta
     */
    public boolean puedeEliminarMozo(int idMozo) throws MozoNoEncontradoException, EmpresaAbiertaException{
        return state.puedeEliminarMozo(idMozo);
    }

    //METODOS MESAS

    /**
     * Retorna si el sistema puede eliminar una mesa
     * pre: nroMesa >= 0
     * @param nroMesa numero de la mesa
     * @return Retorna si la mesa puede ser eliminada
     * @throws MesaNoEncontradaException : Si no se encuentra la mesa
     * @throws MesaYaOcupadaException : Si la mesa se encuentra ocupada
     */
    public boolean puedeEliminarMesa(int nroMesa) throws MesaNoEncontradaException, MesaYaOcupadaException{
        return state.puedeEliminarMesa(nroMesa);
    }

    //METODOS PRODUCTOS

    /**
     * Retorna si el sistema puede eliminar un producto
     * @param idProducto : id del producto a eliminar
     * @return Si se puede eliminar un producto
     * @throws ProductoEnPedidoException Si el producto se encuentra en un pedido
     */
    public boolean puedeEliminarProducto(int idProducto) throws ProductoNoEncontradoException, ProductoEnPedidoException{
        return state.puedeEliminarProducto(idProducto);
    }

    //PERSITENCIA

    public void recuperarEmpresa() {
        try{
            GestorEmpresaDTO pers = new GestorEmpresaDTO();
            pers.recuperarDatos();
            promociones.setPromoTemp(pers.getPromocionTemporales());
            promociones.setPromoProduct(pers.getPromocionesProducto());
            comandas = pers.getComandas();
            asignacionMozosMesas = pers.getAsignacionMozosMesas();
            state = pers.getInstanceState(this);
            Promocion.setNroPromociones(pers.getNroPromocion());
            invariante();
        } catch(ArchivoNoInciliazadoException | IOException | ClassNotFoundException e){

        }

    }

    public void guardarEmpresa() throws ArchivoNoInciliazadoException, IOException {
        GestorEmpresaDTO persitencia = new GestorEmpresaDTO(this);
        persitencia.almacenarDatos();
        invariante();
    }

    private void invariante(){
        assert state != null : "El estado no puede ser nulo";
        assert asignacionMozosMesas != null : "La lista de asignaciones no puede ser nula";
        assert comandas != null : "La lista de comandas no puede ser nula";
        assert promociones != null : "El gestor de promociones no puede ser nulo";
    }

}
