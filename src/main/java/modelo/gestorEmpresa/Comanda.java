package modelo.gestorEmpresa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import enums.EstadoComanda;
import exceptions.comandas.ComandaYaCerradaException;
import modelo.configEmpresa.Mesa;

public class Comanda implements Serializable {

    private GregorianCalendar fecha;
    private Mesa mesa;
    private ArrayList<Pedido> listaDePedidos;
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
		this.listaDePedidos = new ArrayList<>();
		this.estado = EstadoComanda.ABIERTA;
	}


	/**
	 * Crea una nueva instancia de una comanda
	 * Pre: La mesa no puede ser nula;
	 * @param mesa : Mesa que se le asigna a la comanda
	 * @param pedidos : pedidos de la comanda
	 * @param estado : estado de la comanda
	 * @param fecha : fecha de la comanda
	 */
	public Comanda(Mesa mesa, ArrayList<Pedido> pedidos, EstadoComanda estado, GregorianCalendar fecha){
		assert mesa != null : "La mesa debe ser no nula";

		this.fecha = fecha;
		this.mesa = mesa;
		this.listaDePedidos = pedidos;
		this.estado = estado;
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

	/**
	 * Determina el estado de una comanda como cerrda
	 * @throws ComandaYaCerradaException Si la comanda ya se encuentra cerrada
	 */
	public void cerrarComanda() throws ComandaYaCerradaException {
		if(this.estado == EstadoComanda.CERRADA)
			throw new ComandaYaCerradaException();
		this.estado = EstadoComanda.CERRADA;
	}

	/**
	 * retorna si existe un producto con un id ingresado en los pedidos
	 * pre: idProd >= 0
	 * @param productoId id del producto buscado
	 * @return Si existe un producto con el id ingresado
	 */
	public boolean getProductoInPedido(int productoId){
		assert productoId >= 0 : "El id no debe ser negativo";
		boolean res = false;
		int i = 0;
		while (i < listaDePedidos.size() && !res){
			if(listaDePedidos.get(i).getProducto().getId() == productoId)
				res = true;
			i++;
		}
		return res;
	}

	/**
	 * Retorna la fecha de la comanda
	 * @return fecha de la comanda
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * Retorna la mesa de la comanda
	 * @return mesa de la comanda
	 */
	public Mesa getMesa() {
		return mesa;
	}

	/**
	 * Retorna la lista de los pedidos
	 * @return lista de pedidos
	 */
	public ArrayList<Pedido> getListaDePedidos() {
		return listaDePedidos;
	}

	/**
	 * Retorna el estado de la comanda
	 * @return estado de comanda
	 */
	public EstadoComanda getEstado() {
		return estado;
	}

	/**
	 * Retorna la informacion de la comanda en forma de string
	 * @return informacion de la comanda en forma de string
	 */
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : hh:mm");
		return String.format("%s %d %s", sdf.format(fecha.getTime()), mesa.getNroMesa(), estado.toString());
	}
    
}
