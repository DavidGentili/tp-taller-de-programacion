package modelo.empresa;

import java.util.Collection;
import java.util.Date;

import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Producto;

public class Comanda {

    private Date fecha;
    private Mesa mesa;
    private Collection<Pedido> listaDePedidos;
    private String estado;
    
    
    
    
    /**
     * Se encarga de agregar un pedido a la comanda
     * @param prod : producto a agregar
     * @param cant : la cantidad que se desea pedir
     * pre : prod != null
     * pre: cant>0
     * post : se agrega un nuevo pedido a la coleccion
     */
    
	public void AgregarPedido(Producto prod,int cant) {
		
	}

	public Date getFecha() {
		return fecha;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public Collection<Pedido> getListaDePedidos() {
		return listaDePedidos;
	}

	public String getEstado() {
		return estado;
	}
	
	
    
}
