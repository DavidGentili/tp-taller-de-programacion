package modelo.configEmpresa;

import exceptions.StockInsuficienteException;

import java.io.Serializable;

public class Producto implements Serializable {
    private static int nroProducto = 0;
    private int id;
    private String nombre;
    private double precioCosto;
    private double precioVenta;
    private int stock;

    /**
     * Crea un nuevo producto
     * pre: nombre != null && nombre != ""
     *      precioCosto <= precioVenta
     *      stockInicial >= 0
     * post: crea un nuevo producto
     * @param nombre : nombre del producto
     * @param precioCosto : Precio de costo del producto
     * @param precioVenta : precio de venta del producto
     * @param stockInicial : stock inicial del producto
     *
     */
    public Producto(String nombre, double precioCosto, double precioVenta, int stockInicial){}

    /**
     * Crea un nuevo producto con stock inicial igual a 0
     * pre: nombre != null && nombre != ""
     *      precioCosto <= precioVenta
     *      precioCosto > 0
     *      precioVenta > 0
     * @param nombre : nombre del producto
     * @param precioCosto : precio de costo del producto
     * @param precioVenta : precio de venta del producto
     */
    public Producto(String nombre, double precioCosto, double precioVenta){}

    /**
     * Retorna el id de un producto
     * @return id del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nombre del producto
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el precio de costo del producto
     * @return precio de costo
     */
    public double getPrecioCosto() {
        return precioCosto;
    }

    /**
     * Retorna el precio de venta del producto
     * @return precio de venta del producto
     */
    public double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * retorna el stock del producto
     * @return stock del producto
     */
    public int getStock() {
        return stock;
    }

    /**
     * Determina un nuevo id del producto
     * @param id : Nuevo id del producto
     */
    protected void setId(int id){}

    /**
     * Determina el nuevo nombre del producto
     * @param nombre : nuevo nombre del producto
     */
    protected void setNombre(String nombre){}

    /**
     * Determina el precio de costo del producto
     * pre: precioCosto <= this.precioVenta && precioCosto > 0
     * @param precioCosto : precio de costo del producto
     */
    protected void setPrecioCosto(double precioCosto){}

    /**
     * Determina el precio de venta del producto
     * pre: precioVenta >= this.precioCosto && precioVenta > 0
     * @param precioVenta : precio de venta del producto
     */
    protected void setPrecioVenta(double precioVenta){}

    /**
     * Determina el stock del producto
     * pre: stock >= 0
     * @param stock : stock del producto
     */
    protected void setStock(int stock){}

    /**
     * Reduce el stock del producto en 1
     * pre:
     * post: this.cantidad = oldCantidad - 1
     * @throws StockInsuficienteException si el stock no es suficiente para realizar la accion indicada
     */
    public void reducirStock() throws StockInsuficienteException {}

    /**
     * Reduce el stock del producto en la cantidad indicada si la
     * cantidad supera el stock establecido, retorna excepcion
     * pre: cantidad > 0;
     * post this.cantidad = oldCantidad - cantidad
     * @param cantidad : cantidad de stock que se desea restar
     * @throws StockInsuficienteException si el stock no es suficiente para realizar la accion indicada
     */
    public void reducirStock(int cantidad) throws StockInsuficienteException {}
}
