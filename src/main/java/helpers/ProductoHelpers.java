package helpers;

import exceptions.controlador.ErrorAlCrearProductoException;

public class ProductoHelpers {
    public static void checkNombreProducto(String name) throws ErrorAlCrearProductoException {
        if(name == null || name.isBlank() || name.isEmpty())
            throw new ErrorAlCrearProductoException("El nombre es incorrecto");
    }

    public static void checkPrecios(double costo, double venta) throws ErrorAlCrearProductoException {
        if(costo < 0 || venta < 0 || costo >= venta)
            throw new ErrorAlCrearProductoException("Los precios del producto son incorrectos");
    }
}
