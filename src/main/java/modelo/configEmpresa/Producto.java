package modelo.configEmpresa;

import exceptions.productos.StockInsuficienteException;

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
    public Producto(String nombre, double precioCosto, double precioVenta, int stockInicial){
        assert nombre != null && !nombre.isEmpty() && !nombre.isBlank() : "Nombre de producto incorrecto";
        assert precioCosto <= precioVenta : "El precio de venta tiene que ser mayor al de costo";
        assert stockInicial > 0 : "El sock inicial debe ser mayor a 0";

        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.stock = stockInicial;
        this.id = nroProducto;
        nroProducto++;

        assert this.nombre == nombre : "No se asigno correctamente el nombre";
        assert this.precioCosto == precioCosto : "No se asigno correctamente el precio de costo";
        assert this.precioVenta == precioVenta : "No se asigno correctamente el precio de venta";
        assert this.stock == stockInicial : "No se asigno correctamente el stock inicial";
        assert this.id == nroProducto  - 1 : "No se asigno correctamente el id";
    }

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
    public Producto(String nombre, double precioCosto, double precioVenta){
        assert nombre != null && !nombre.isEmpty() && !nombre.isBlank() : "Nombre de producto incorrecto";
        assert precioCosto <= precioVenta : "El precio de venta tiene que ser mayor al de costo";

        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.id = nroProducto;
        nroProducto++;

        assert this.nombre == nombre : "No se asigno correctamente el nombre";
        assert this.precioCosto == precioCosto : "No se asigno correctamente el precio de costo";
        assert this.precioVenta == precioVenta : "No se asigno correctamente el precio de venta";
        assert this.id == nroProducto  - 1 : "No se asigno correctamente el id";
    }

    /**
     * Retorna en numero de mozos que se han almacenado
     * @return Nro de mozos
     */
    public static int getNroProducto(){
        return nroProducto;
    }


    protected static void setNroProducto(int nroProducto){
        assert nroProducto >= 0 : "El numero de producto no puede ser negativo";

        Producto.nroProducto = nroProducto;

        assert Producto.nroProducto == nroProducto : "No se asigno correctamente el numero del producto";
    }

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
     * pre: id >= 0
     * @param id : Nuevo id del producto
     */
    protected void setId(int id){
        assert id >= 0 : "El id no puede ser negativo";

        this.id = id;

        invariante();
        assert this.id == id : "No se asigno correctamente el id";
    }

    /**
     * Determina el nuevo nombre del producto
     * pre: nombre != null && nombre != ""
     * @param nombre : nuevo nombre del producto
     */
    protected void setNombre(String nombre){
        assert nombre != null && !nombre.isEmpty() && !nombre.isBlank() : "Nombre de producto incorrecto";

        this.nombre = nombre;

        invariante();
        assert this.nombre == nombre : "No se asigno correctamente el nombre";
    }

    /**
     * Determina el precio de costo del producto
     * pre: precioCosto <= this.precioVenta && precioCosto > 0
     * @param precioCosto : precio de costo del producto
     */
    protected void setPrecioCosto(double precioCosto){
        assert precioCosto < this.precioVenta && precioCosto > 0 : "El precio de costo no puede ser mayor que el de venta, tampoco puede ser <= 0";

        this.precioCosto = precioCosto;

        invariante();
        assert this.precioCosto == precioCosto : "No se asigno correctamente el precio de costo";
    }

    /**
     * Determina el precio de venta del producto
     * pre: precioVenta >= this.precioCosto && precioVenta > 0
     * @param precioVenta : precio de venta del producto
     */
    protected void setPrecioVenta(double precioVenta){
        assert this.precioCosto < precioVenta && precioVenta > 0 : "El precio de venta no puede ser mayor que el de costo, tampoco puede ser <= 0";

        this.precioVenta = precioVenta;

        invariante();
        assert  this.precioVenta == precioVenta : "No se asigno correctamente el precio de venta";
    }

    /**
     * Determina el stock del producto
     * pre: stock >= 0
     * @param stock : stock del producto
     */
    protected void setStock(int stock){
        assert stock >= 0 : "El stock no puede ser negativo";

        this.stock = stock;

        invariante();
        assert this.stock == stock : "No se asigno corretamente el stock";
    }

    /**
     * Reduce el stock del producto en 1
     * pre:
     * post: this.cantidad = oldCantidad - 1
     * @throws StockInsuficienteException si el stock no es suficiente para realizar la accion indicada
     */
    public void reducirStock() throws StockInsuficienteException {
        int oldStock = stock;

        if(stock == 0)
            throw new StockInsuficienteException();
        stock--;

        invariante();
        assert stock == oldStock - 1 : "No se resto correcctamente el stock";
    }

    /**
     * Reduce el stock del producto en la cantidad indicada si la
     * cantidad supera el stock establecido, retorna excepcion
     * pre: cantidad > 0;
     * post this.cantidad = oldCantidad - cantidad
     * @param cantidad : cantidad de stock que se desea restar
     * @throws StockInsuficienteException si el stock no es suficiente para realizar la accion indicada
     */
    public void reducirStock(int cantidad) throws StockInsuficienteException {
        int oldStock = stock;
        if(stock < cantidad)
            throw new StockInsuficienteException();
        stock -= cantidad;

        invariante();
        assert stock == oldStock - cantidad : "No se resto correctamente el stock";
    }

    protected void updateProducto(Producto other){
        this.nombre = other.nombre;
        this.precioCosto = other.precioCosto;
        this.precioVenta = other.precioVenta;
        this.stock = other.stock;
        invariante();
    }

    private void invariante(){
        assert precioCosto < precioVenta : "El precio de costo debe ser menor al precio de venta";
        assert stock >= 0 : "El stock no puede ser negativo";
    }

    @Override
    public String toString() {
        return String.format("%4d %-12s %6.1f %6.1f %d", id, nombre, precioCosto, precioVenta, stock);
    }
}
