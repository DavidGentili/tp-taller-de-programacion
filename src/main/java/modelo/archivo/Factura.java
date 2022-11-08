package modelo.archivo;

import java.util.Collection;
import java.util.Date;

import modelo.configEmpresa.Mesa;
import modelo.empresa.Pedido;
import modelo.empresa.Promocion;

/**
 * Clase desarrollada para instanciar las facturas generadas por el sistema.
 * También nos permitirá consultar los diferentes valores de la misma.
 * ESTA CLASE REQUIERE TENER ACCESO A LAS PROMOCIONES VIGENTES
 * @author
 *
 */
public class Factura {

    private Date fecha;
    private Mesa mesa;
    private Collection<Pedido> pedidos;
    private double total;
    private String formaDePago;
    private Collection<Promocion> promocionesAplicadas;


    /**
     * Constructor para instanciar una factura dentro del sistema
     * la fecha de factura es la fecha de realización de la misma
     * la clase tiene acceso al listado de promociones para poder calcular los descuentos asociados.
     * @param mesa para la cual se desea realizar la factura
     * @param listado de pedidos realizados por esa mesa 
     * @param forma de pago en el que desea pagar el cliente
     * pre: pedidos no puede estar vació, mesa debe ser distinto de null, la forma de pago se debe encontrar dentro los tipos enunciados en los enums
     * post: Instancia la factura con los datos relacionados a la misma
     */
    Factura(Mesa mesa,Collection<Pedido> pedidos, String formaDePago){}
    
    
    /**
     * Consulta al registro factura
     * @return fecha de la factura instanciada
     */
    public Date getFecha() {return fecha;}

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
	public Collection<Pedido> getPedidos() {
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
	public String getFormaDePago() {
		return formaDePago;
	}
	

	/**
	 * Consulta al registro factura
	 * @return promociones aplicadas en la factura instanciada
	 */
	public Collection<Promocion> getPromocionesAplicadas() {return promocionesAplicadas;}
	
	
	/**
	 * Ganera el listado de promociones a aplicar para la factura
	 * @return listado de promociones que se deben aplicar en la factura instanciada
	 * Esta clase debe tener acceso al listado de promociones para poder realizar el cálculo correspondiente
	 */
	public Collection<Promocion> promocionesAplicadas() {Collection<Promocion> listaPromociones = null; return listaPromociones;}
	
	
	/**
	 * Calcula el Total del monto a facturar
	 * @param pedidos
	 * @param formaDePago
	 * @return Total a a abonar para la factura realizada
	 * pre: listado de pedidos no puede estar vacío y forma de pago se debe encontrar dentro los tipos enunciados en los enums
	 */
	public double calculaTotal(Collection<Pedido> pedidos, String formaDePago) {return 0;}
    
    
}
