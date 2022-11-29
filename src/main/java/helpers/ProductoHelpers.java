package helpers;

import exceptions.controlador.ErrorAlCrearProductoException;

/**
 * Clase con metodos estaticos, encargada de asistir en las cuestiones relacionadas con los productos
 */
public class ProductoHelpers {

    /**
     * Se encarga de comrpobar si el nombre de unproducto es valido
     * @param name nombre del producto
     * @throws ErrorAlCrearProductoException si el nombre del producto no es valido
     */
    public static void checkNombreProducto(String name) throws ErrorAlCrearProductoException {
        if(name == null || name.isBlank() || name.isEmpty())
            throw new ErrorAlCrearProductoException("El nombre es incorrecto");
    }

    /**
     * Se encarga de comprobar si los precios de un producto son validos
     * @param costo precio de costo
     * @param venta precio de venta
     * @throws ErrorAlCrearProductoException si el costo o la venta son negativos, o costo es mayor o igual a venta
     */
    public static void checkPrecios(double costo, double venta) throws ErrorAlCrearProductoException {
        if(costo < 0 || venta < 0 || costo >= venta)
            throw new ErrorAlCrearProductoException("Los precios del producto son incorrectos");
    }
}
