package modelo.archivo;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import enums.FormasDePago;
import modelo.configEmpresa.Mesa;
import modelo.gestorEmpresa.Pedido;
import modelo.gestorEmpresa.Promocion;

/**
 * Clase desarrollada para instanciar las facturas generadas por el sistema.
 * También nos permitirá consultar los diferentes valores de la misma.
 * ESTA CLASE REQUIERE TENER ACCESO A LAS PROMOCIONES VIGENTES
 * @author
 *
 */
public class Factura {
	private static int nroFactura = 0;
	private int id;
    private GregorianCalendar fecha;
    private Mesa mesa;
    private ArrayList<Pedido> pedidos;
    private double total;
    private FormasDePago formaDePago;
    private ArrayList<Promocion> promocionesAplicadas;

	public static void setNroFactura(int nroFactura) {
		Factura.nroFactura = nroFactura;
	}

	public static int getNroFactura() {
		return nroFactura;
	}

	public int getId() {
		return id;
	}

	/**
     * Constructor para instanciar una factura dentro del sistema
     * la fecha de factura es la fecha de realización de la misma
     * la clase tiene acceso al listado de promociones para poder calcular los descuentos asociados.
     * @param mesa para la cual se desea realizar la factura
     * @param pedidos lista de pedidos realizados por esa mesa
	 * @param promocionesAplicadas : las promociones que se van a aplicar
     * @param formaDePago de pago en el que desea pagar el cliente
     * pre: pedidos != null
	 *      mesa != null
	 *      la forma de pago se debe encontrar dentro los tipos enunciados en los enums
     * post: Instancia la factura con los datos relacionados a la misma
     */
    public Factura(Mesa mesa, ArrayList<Pedido> pedidos, ArrayList<Promocion> promocionesAplicadas, FormasDePago formaDePago){
		assert mesa != null : "La mesa no puede ser nula";
		assert pedidos != null : "Los pedidos no pueden ser nulos";
		assert promocionesAplicadas != null : "Las promociones no pueden ser nulas";
		assert formaDePago != null : "La forma de pago no puede ser nula";

		this.fecha = (GregorianCalendar) GregorianCalendar.getInstance();
		this.mesa = getCloneMesa(mesa);
		this.pedidos = getClonePedido(pedidos);
		this.formaDePago = formaDePago;
		this.promocionesAplicadas = promocionesAplicadas;
		this.total = calculaTotal();
		this.id = nroFactura;
		nroFactura++;
	}

	private ArrayList<Pedido> getClonePedido(ArrayList<Pedido> pedidos){
		ArrayList<Pedido> clon = new ArrayList<>();
		for(Pedido pedido : pedidos){
			try{
				clon.add((Pedido) pedido.clone());
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
		return clon;
	}

	private Mesa getCloneMesa(Mesa mesa){
		Mesa clon;
		try{
			clon = (Mesa) mesa.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return mesa;
	}
    
    /**
     * Consulta al registro factura
     * @return fecha de la factura instanciada
     */
    public GregorianCalendar getFecha() {return fecha;}

    /**
     * Consulta al registro factura
     * @return mesa asociada a la factura instanciada
     */
    public Mesa getMesa() {
		return mesa;
	}

    
    /**
     * Consulta al registro factura
     * @return lista de pedidos relacionado a la factura instanciada
     */
	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	
	/**
	 * Consulta al registro factura
	 * @return precio Total de la factura instanciada
	 */
	public double getTotal() {
		return total;
	}

	
	/**
	 * Consulta al registro factura
	 * @return forma de pago de la factura instanciada
	 */
	public FormasDePago getFormaDePago() {
		return formaDePago;
	}
	

	/**
	 * Consulta al registro factura
	 * @return promociones aplicadas en la factura instanciada
	 */
	public ArrayList<Promocion> getPromocionesAplicadas() {return promocionesAplicadas;}

	
	
	/**
	 * Calcula el Total del monto a facturar
	 * @return Total a a abonar para la factura realizada
	 */
	private double calculaTotal() {
		double total = 0;
		double desc = 0;
		for( Pedido pedido : pedidos )
			total += pedido.getCantidad() * pedido.getProducto().getPrecioVenta();
		for(Promocion promocion : promocionesAplicadas)
			desc += promocion.getDescuento(pedidos);
		return total - desc;
	}
    
    
}
