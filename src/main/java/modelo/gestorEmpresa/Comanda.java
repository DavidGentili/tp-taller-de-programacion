package modelo.gestorEmpresa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import enums.EstadoComanda;
import exceptions.ComandaYaCerradaException;
import modelo.configEmpresa.Mesa;

public class Comanda {

    private GregorianCalendar fecha;
    private Mesa mesa;
    private Collection<Pedido> listaDePedidos;
    private EstadoComanda estado;

	/**
	 * Crea una nueva instancia de una comanda
	 * Pre: La mesa no puede ser nula;
	 * @param mesa : Mesa que se le asigna a la comanda
	 */
	public Comanda(Mesa mesa){
		assert mesa != null : "La mesa debe ser no nula";

		this.fecha = (GregorianCalendar) GregorianCalendar.getInstance();
		this.mesa = mesa;
		this.listaDePedidos = new ArrayList<Pedido>();
		this.estado = EstadoComanda.ABIERTA;
	}
    
    
    /**
     * Se encarga de agregar un pedido a la comanda
     * @param pedido : producto a agregar
     * pre : pedido != null
     * post : se agrega un nuevo pedido a la coleccion
     */
	public void agregarPedido(Pedido pedido) throws ComandaYaCerradaException {
		assert pedido != null : "El pedido no puede ser nulo";

		if(this.estado != EstadoComanda.ABIERTA)
			throw new ComandaYaCerradaException();
		this.listaDePedidos.add(pedido);

		assert this.listaDePedidos.contains(pedido) : "No se asigno correctamente el pedido";
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public Collection<Pedido> getListaDePedidos() {
		return listaDePedidos;
	}

	public EstadoComanda getEstado() {
		return estado;
	}
	
	
    
}
