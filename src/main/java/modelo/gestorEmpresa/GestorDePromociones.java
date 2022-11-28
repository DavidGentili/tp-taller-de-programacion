package modelo.gestorEmpresa;

import exceptions.promociones.PromocionNoEncontradaException;
import exceptions.promociones.PromocionYaExistenteException;

import java.io.Serializable;
import java.util.ArrayList;

public class GestorDePromociones implements Serializable {
    private ArrayList<PromocionTemp> promoTemp;
    private ArrayList<PromocionProducto> promoProduct;

    /**
     * Instancia un gestor de promocion, sin promociones
     */
    public GestorDePromociones(){
        promoProduct = new ArrayList<PromocionProducto>();
        promoTemp = new ArrayList<PromocionTemp>();

        invariante();
    }

    /**
     * Retorna las promociones almacenadas
     * @return Promociones almacenadas
     */
    protected ArrayList<Promocion> getPromociones(){
        ArrayList<Promocion> promociones = new ArrayList<Promocion>();
        promociones.addAll(promoProduct);
        promociones.addAll(promoTemp);
        return promociones;
    }

    /**
     * Retorna las promociones temporales almacenadas
     * @return Promociones temporales
     */
    protected ArrayList<PromocionProducto> getPromocionProducto(){
        return promoProduct;
    }

    /**
     * Retorna las promociones temporales
     * @return promociones temporales
     */
    protected ArrayList<PromocionTemp> getPromoTemp() {
        return promoTemp;
    }

    /**
     * Determina una lista de promociones temporales
     * @param promoTemp lista de promociones temporales
     */
    protected void setPromoTemp(ArrayList<PromocionTemp> promoTemp) {
        assert promoTemp != null : "La lista de promociones no puede ser nula";
        this.promoTemp = promoTemp;

        invariante();
        assert this.promoTemp == promoTemp : "No se asigno correctamente la lista de promociones temporales";
    }

    /**
     * Retorna las promociones de productos
     * @return promociones de producto
     */
    protected ArrayList<PromocionProducto> getPromoProduct() {
        return promoProduct;
    }

    /**
     * Determina una lista de promociones de producto
     * @param promoProduct lista de promociones de producto
     */
    protected void setPromoProduct(ArrayList<PromocionProducto> promoProduct) {
        assert promoProduct != null : "Las promociones producto no pueden ser nulas";

        this.promoProduct = promoProduct;

        invariante();
        assert this.promoProduct == promoProduct : "No se asigno correctamente la promocion de producto";
    }

    /**
     * Retorna las promociones de producto almacenadas
     * @return Promociones de producto
     */
    protected ArrayList<PromocionTemp> getPromocionTemporales(){
        return promoTemp;
    }

    /**
     * Retorna la promocion correspondiente la id o null
     * @param id : id de la promocion buscada
     * @return : promocion correspondiente o null
     */
    protected Promocion getPromocionById(int id){
        assert id >= 0 : "El id no puede ser negativo";
        ArrayList<Promocion> promociones = getPromociones();
        Promocion promo = null;
        int i = 0;
        while(i < promociones.size() && promo == null){
            if(promociones.get(i).getId() == id)
                promo = promociones.get(i);
            i++;
        }
        return promo;
    }

    /**
     * Agrega una promocion de producto a la coleccion
     * pre: promo != null
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    protected void agregarPromocionProducto(PromocionProducto promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";

        if(getPromocionById(promo.getId()) != null)
            throw new PromocionYaExistenteException();
        promoProduct.add(promo);

        invariante();
        assert promoProduct.contains(promo) : "No se agrego correctamente la promocion";
    }

    /**
     * Agrega una promocion temporal a la coleccion
     * pre : promo != null
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    protected void agregarPromocionTemp(PromocionTemp promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";

        if(getPromocionById(promo.getId()) != null)
            throw new PromocionYaExistenteException();
        promoTemp.add(promo);

        invariante();
        assert promoTemp.contains(promo) : "No se agrego correctamente la promocion";
    }

    /**
     * Elimina la promocion correspondiente con el id ingresado
     * @param id : Id de la promocion a eliminar
     * @throws PromocionNoEncontradaException : Si no se encuentra la promocion correspondiente
     */
    protected void eliminarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";
        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        if(promoProduct.contains(promo))
            promoProduct.remove(promo);
        else
            promoTemp.remove(promo);

        invariante();
        assert !getPromociones().contains(promo) : "No se elimino correctamente la promocion";
    }

    /**
     * Activa la promocion correspondiente al id
     * @param id : Id de la promocion que se quiere activar
     * @throws PromocionNoEncontradaException : Si la promocion buscada no se encuentra
     */
    protected void activarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        ArrayList<Promocion> promociones = getPromociones();
        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.activarPromocion();

        invariante();
        assert promo.isActiva() : "No se desactivo correctamente la promocion";
    }

    /**
     * Desactiva la promocion correspondiente al id
     * @param id : Id de la promocion que se quiere desactivar
     * @throws PromocionNoEncontradaException : Si la promocion buscada no se encuentra
     */
    protected void desactivarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        ArrayList<Promocion> promociones = getPromociones();
        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.desactivarPromocion();

        invariante();
        assert !promo.isActiva() : "No se desactivo correctamente la promocion";
    }

    /**
     * Invariante de clase
     */
    private void invariante(){
        assert promoTemp != null : "La lista de promociones temporales no puede ser nula";
        assert promoProduct != null : "La lista de promociones de producto no puede ser nula";
    }
}
