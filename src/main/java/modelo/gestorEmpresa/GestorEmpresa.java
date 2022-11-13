
package modelo.gestorEmpresa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import enums.EstadoMozos;
import exceptions.*;
import modelo.archivo.Archivo;
import modelo.configEmpresa.*;

public class GestorEmpresa {
    private static GestorEmpresa instance = null;
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private Collection<Comanda> comandas;
    private Collection<MozoMesa> asignacionMozosMesas;
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

    public void abrirEmpresa(){
        state.abrirEmpresa();
    }

    public void cerrarEmpresa() throws EmpresaCerradaException {
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
     * @param mozo : el mozo al que se le asignara la mesa
     * @param mesa : la mesa que se le asignara al mozo
     * @param dia : dia especifico en que va a ocurrir esta asignacion
     * pre: mozo debe ser distinto de null
     * post: se añadira una nueva asignacion a la coleccion 
     */
    public void asignarMozo(Mozo mozo,Mesa mesa,Date dia) {
    	
    }

    //METODOS COMANDAS

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
    public void cerrarComanda(int nroMesa) throws MesaYaLiberadaException, EmpresaCerradaException {
        state.cerrarComanda(nroMesa);
    }

    public void agregarPedido(int nroMesa, Pedido pedido){
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
     * Agrega una nueva promocion
     * pre: promocion != null
     * @param promocion : La nueva promocion a añadir
     */
    public void agregaPromocion(Promocion promocion) throws PromocionYaExistenteException {
        assert promocion != null : "La promocion no puede ser nula";
        promociones.agregarPromocion(promocion);
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

    public void agregaMozo(Mozo nuevoMozo, Operario user){
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
    public void definirEstadoMozo(int mozoId, EstadoMozos estado){
        state.definirEstadoMozo(mozoId, estado);
    }


    public void eliminaMozo(int mozoId, Operario user){
        state.eliminaMozo(mozoId, user);
    }

    //METODOS CONFIGURACION: MESAS
    public ArrayList<Mesa> getMesas(){
        return configuracion.getMesas();
    }

    public void agregarMesa(Mesa mesa, Operario user) throws MesaYaExistenteException, UsuarioNoAutorizadoException {
        configuracion.agregarMesa(mesa, user);
    }

    public void actualizarMeza(Mesa mesa, int nroMesa, Operario user) throws MesaNoEncontradaException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actulizarMesa(mesa,nroMesa,user);
    }

    public void eliminarMesa(int nroMesa, Operario user){
        state.eliminarMesa(nroMesa, user);
    }

    //METODOS CONFIGURACION: PRODUCTOS

    public ArrayList<Producto> getProductos(){
        return configuracion.getProductos();
    }

    public void agregarProducto(Producto producto, Operario user) throws ProductoYaExistenteException, UsuarioNoAutorizadoException {
        configuracion.agregarProducto(producto, user);
    }

    public void actualizarProducto(Producto producto, int productoId, Operario user) throws ProductoNoEncontradoException, IdIncorrectoException, UsuarioNoAutorizadoException {
        configuracion.actulizarProducto(producto,productoId,user);
    }

    public void eliminarProducto(int productoId, Operario user){
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
