
package modelo.gestorEmpresa;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import enums.EstadoMozos;
import exceptions.*;
import modelo.archivo.Archivo;
import modelo.configEmpresa.*;

public class GestorEmpresa {
    private static GestorEmpresa instance = null;
    protected ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private ArrayList<Comanda> comandas;
    private ArrayList<MozoMesa> asignacionMozosMesas;
    private GestorDePromociones promociones;
    private StateGestorEmpresa state;

    /*
    * ESTADOS EMPRESA:
    * Abierta
    * Cerrada
    */

    private GestorEmpresa(){}

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

    public void eliminarRelacionMozoMesa(int nroMesa) throws EmpresaAbiertaException {
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
    public void cerrarComanda(int nroMesa) throws MesaYaLiberadaException, EmpresaCerradaException, ComandaYaCerradaException, MesaNoEncontradaException {
        state.cerrarComanda(nroMesa);
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

    /**
     * Retorna las promociones temporales almacenadas
     * @return promociones temprales
     */
    public ArrayList<PromocionTemp> getPromocionesTemporales(){
        return promociones.getPromocionTemporales();
    }

    /**
     * Agrega una promocion de producto a la coleccion
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    protected void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";
        promociones.agregarPromocionProducto(promo);
    }

    /**
     * Agrega una promocion temporal a la coleccion
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    protected void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException {
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

    //METODOS CONFIGURACION: GENERAL

    protected ConfiguracionEmpresa getConfiguracion(){
        return configuracion;
    }

    public void cambiarNombreLocal(String name, Operario user) throws UsuarioNoLogueadoException, UsuarioNoAutorizadoException {
        configuracion.cambiaNombreLocal(name, user);
    }

    public String getNombreLocal(){
        return configuracion.getNombreLocal();
    }

    public void setSueldo(Sueldo sueldo, Operario user) throws UsuarioNoAutorizadoException {
        configuracion.setSueldo(sueldo, user);
    }

    public Sueldo getSueldo(){
        return configuracion.getSueldo();
    }

    //METODOS CONFIGURACION: MOZOS

    public ArrayList<Mozo> getMozos(){
        return configuracion.getMozos();
    }

    public Mozo getMozoById(int mozoId){
        return configuracion.getMozoById(mozoId);
    }

    public void agregaMozo(Mozo nuevoMozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException, EmpresaAbiertaException {
        state.agregarMozo(nuevoMozo, user);
    }

    public void actualizaMozo(Mozo mozoActualizado, int idMozo, Operario user) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actualizarMozo(mozoActualizado, idMozo, user);
    }

    /**
     * Define el estado de un mozo
     * pre: estado != null
     * @param mozoId Id del mozo
     * @param estado Estado del mozo;
     */
    public void definirEstadoMozo(int mozoId, EstadoMozos estado) throws MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException {
        state.definirEstadoMozo(mozoId, estado);
    }

    public void eliminaMozo(int mozoId, Operario user) throws MozoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, EmpresaAbiertaException {
        state.eliminaMozo(mozoId, user);
    }

    //METODOS CONFIGURACION: MESAS
    public ArrayList<Mesa> getMesas(){
        return configuracion.getMesas();
    }

    public Mesa getMesaByNroMesa(int nroMesa){
        return configuracion.getMesaNroMesa(nroMesa);
    }

    public void agregarMesa(Mesa mesa, Operario user) throws MesaYaExistenteException, UsuarioNoAutorizadoException {
        configuracion.agregarMesa(mesa, user);
    }

    public void actualizarMeza(Mesa mesa, int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actulizarMesa(mesa,nroMesa,user);
    }

    public void eliminarMesa(int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException, MesaYaOcupadaException {
        state.eliminarMesa(nroMesa, user);
    }

    //METODOS CONFIGURACION: PRODUCTOS

    public ArrayList<Producto> getProductos(){
        return configuracion.getProductos();
    }

    protected Producto getProductoById(int productoId){
        return configuracion.getProductoById(productoId);
    }
    public void agregarProducto(Producto producto, Operario user) throws ProductoYaExistenteException, UsuarioNoAutorizadoException {
        configuracion.agregarProducto(producto, user);
    }

    public void actualizarProducto(Producto producto, int productoId, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actulizarProducto(producto,productoId,user);
    }

    public void eliminarProducto(int productoId, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException, ProductoEnPedidoException {
        state.eliminarProducto(productoId, user);
    }

    //METODOS CONFIGURACION: OPERARIOS

    public ArrayList<Operario> getOperarios(Operario user) throws UsuarioNoAutorizadoException {
        return configuracion.getOperarios(user);
    }

    public void agregarOperario(Operario nuevo, Operario user) throws OperarioYaExistenteException, UsuarioNoAutorizadoException {
        configuracion.agregarOperario(nuevo, user);
    }

    public void actualizarOperario(Operario actualizado, int operarioId, Operario user) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actualizarOperario(actualizado, operarioId, user);
    }

    public void eliminarOperario(int operarioId, Operario user) throws OperarioNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.eliminarOperario(operarioId, user);
    }

    //PERSITENCIA

    public void recuperarEmpresa(){

    }

    public void guardarEmpresa(){

    }



}
