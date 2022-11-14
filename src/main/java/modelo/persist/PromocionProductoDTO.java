package modelo.persist;

import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Producto;
import modelo.gestorEmpresa.Promocion;
import modelo.gestorEmpresa.PromocionProducto;

import java.io.Serializable;

public class PromocionProductoDTO implements Serializable {

    private int id;
    private String dias;
    private int idProducto;
    private boolean dosPorUno;
    private boolean dtoPorCant;
    private int cantMinima;
    private double precioUnitario;

    public PromocionProductoDTO(PromocionProducto promocion){
        this.id = promocion.getId();
        this.dias = promocion.getDias();
        this.idProducto = promocion.getProducto().getId();
        this.dosPorUno = promocion.isDosPorUno();
        this.dtoPorCant = promocion.isDtoPorCant();
        this.cantMinima = promocion.getCantMinima();
        this.precioUnitario = promocion.getPrecioUnitario();
    }

    public PromocionProducto getPromocionProducto(){
        Producto producto = ConfiguracionEmpresa.getInstance().getProductoById(idProducto);
        PromocionProducto promo = new PromocionProducto(dias, producto, dosPorUno, dtoPorCant, cantMinima, precioUnitario);
        promo.setId(id);
        return promo;
    }


}
