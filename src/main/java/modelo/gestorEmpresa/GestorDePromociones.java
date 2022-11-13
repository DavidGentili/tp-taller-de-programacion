package modelo.gestorEmpresa;

import exceptions.PromocionNoEncontradaException;
import exceptions.PromocionYaExistenteException;

import java.util.ArrayList;

public class GestorDePromociones {
    private ArrayList<Promocion> promociones;

    public GestorDePromociones(){
        promociones = new ArrayList<Promocion>();
    }

    protected ArrayList<Promocion> getPromociones(){
        return promociones;
    }

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

    protected void agregarPromocion(Promocion promo) throws PromocionYaExistenteException {
        assert promo != null : "La promocion no puede ser nula";

        if(getPromocionById(promo.getId()) != null)
            throw new PromocionYaExistenteException();
        promociones.add(promo);

        assert promociones.contains(promo) : "No se agrego correctamente la promocion";
    }

    protected void eliminarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";
        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promociones.remove(promo);

        assert !promociones.contains(promo) : "No se elimino correctamente la promocion";
    }

    protected void activarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.desactivarPromocion();

        assert !getPromocionById(id).isActiva() : "No se desactivo correctamente la promocion";
    }

    protected void desactivarPromocion(int id) throws PromocionNoEncontradaException {
        assert id >= 0 : "El id no puede ser negativo";

        Promocion promo = getPromocionById(id);
        if(promo == null)
            throw new PromocionNoEncontradaException();
        promo.activarPromocion();

        assert getPromocionById(id).isActiva() : "No se desactivo correctamente la promocion";
    }
}
