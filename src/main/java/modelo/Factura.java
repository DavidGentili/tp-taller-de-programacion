package modelo;

import java.util.Collection;
import java.util.Date;

public class Factura {

    private Date fecha;
    private Mesa mesa;
    private Collection<Pedido> pedidos;
    private double total;
    private String formaDePago;
    private Collection<Promocion> promocionesAplicadas;


}
