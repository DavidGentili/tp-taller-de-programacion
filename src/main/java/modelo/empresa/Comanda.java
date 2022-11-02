package modelo.empresa;

import modelo.configEmpresa.Mesa;

import java.util.Collection;
import java.util.Date;

public class Comanda {

    private Date fecha;
    private Mesa mesa;
    private Collection<Pedido> listaDePedidos;
    private String estado;
}
