package modelo.gestorEmpresa;

import exceptions.PromocionNoEncontradaException;
import exceptions.PromocionYaExistenteException;

import java.util.ArrayList;

public class GestorDePromociones {
    private ArrayList<Promocion> promociones;

    public GestorDePromociones(){
        promociones = new ArrayList<Promocion>();
    }

    /**
     * Retorna las promociones almacenadas
     * @return Promociones almacenadas
     */
    protected ArrayList<Promocion> getPromociones(){
        return promociones;
    }

    /**
     * Retorna la promocion correspondiente la id o null
     * @param id : id de la promocion buscada
     * @return : promocion correspondiente o null
     */
    protected Promocion getPromocionById(int id){
        assert id >= 0 : "El id no puede ser negativo";

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
     * Agrega una promocion a la coleccion
     * @param promo Promocion a agregar
     * @throws PromocionYaExistenteException : Si dicha promocion ya existe
     */
    protected void agregarPromocion(Promocion promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";

        if(getPromocionById(promo.getId()) != null)
            throw new PromocionYaExistenteException();
        promociones.add(promo);

        assert promociones.contains(promo) : "No se agrego correctamente la promocion";
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
        promociones.remove(promo);

        assert !promociones.contains(promo) : "No se elimino correctamente la promocion";
    }

    /**
     * Activa la promocion correspondiente al id
     * @param id : Id de la promocion que se quiere activar
     * @throws PromocionNoEncontradaException : Si la promocion buscada no se encuentra
     */
    protected void activarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.desactivarPromocion();

        assert !getPromocionById(id).isActiva() : "No se desactivo correctamente la promocion";
    }

    /**
     * Desactiva la promocion correspondiente al id
     * @param id : Id de la promocion que se quiere desactivar
     * @throws PromocionNoEncontradaException : Si la promocion buscada no se encuentra
     */
    protected void desactivarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.activarPromocion();

        assert getPromocionById(id).isActiva() : "No se desactivo correctamente la promocion";
    }
}
