package modelo.empresa;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Pedido;

import java.util.Collection;
import java.util.Date;

public class Comanda {

    private Date fecha;
    private Mesa mesa;
    private Collection<Pedido> listaDePedidos;
    private String estado;
}
