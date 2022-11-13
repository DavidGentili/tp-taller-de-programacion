package modelo.configEmpresa;

import exceptions.IdIncorrectoException;
import exceptions.productos.ProductoNoEncontradoException;
import exceptions.productos.ProductoYaExistenteException;
import exceptions.operarios.UsuarioNoAutorizadoException;

import java.util.ArrayList;

public class GestorDeProductos {
    private ArrayList<Producto> productos;

    public GestorDeProductos(){
        this.productos = new ArrayList<Producto>();
    }

    /**
     * Asigna una lista de productos al gesto
     * pre: productos != null
     * @param productos productos a asignar
     */
    protected void setProductos(ArrayList<Producto> productos){
        assert productos != null : "Los productos no deben ser nulo";
        this.productos = productos;
        assert this.productos == productos : "No se asigno correctamente los productos";
    }

    /**
     * Retorna los productos del sistema
     * @return Los productos del sistema
     */
    public ArrayList<Producto> getProductos() {return productos;};

    /**
     * Retorna el producto correspondiente al id ingresado, en caso de que no exista un producto con dicho id retorna null
     * pre: productoId >= 0;
     * @param productoId : Id del producto deseado
     * @return el producto correspondiente al id ingresado
     */
    public Producto getProductoById(int productoId){
        assert productoId >= 0 : "El id no puede ser negativo";
        Producto producto = null;
        int i = 0;
        while(producto == null && i < productos.size()){
            if(productos.get(i).getId() == productoId)
                producto = productos.get(i);
            i++;
        }
        return producto;
    };


    /**
     * Se agrega un producto al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevoProducto : El producto que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws ProductoYaExistenteException : El producto ya se encuentra en la coleccion
     * pre: nuevoProducto != null
     *      user != null
     * post: Se agrega el producto a la coleccion de mesas
     */
    public void agregarProducto(Producto nuevoProducto, Operario user) throws UsuarioNoAutorizadoException, ProductoYaExistenteException {
        assert productos != null : "El producto no puede ser nulo";
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeGestionarProductos())
            throw new UsuarioNoAutorizadoException();
        if(getProductoById(nuevoProducto.getId()) != null)
            throw new ProductoYaExistenteException();
        productos.add(nuevoProducto);

        assert getProductoById(nuevoProducto.getId()) != null : "No se agrego correctamente el producto";
    };

    /**
     * Se actualiza un producto en el sistema
     * @param productoActualizado : El producto con los valores actualizados
     * @param productoId : el Id del producto
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws ProductoNoEncontradoException : Si no se encuentra el producto buscado
     * pre: productoActualizado != null
     *      user != null
     * post: Se actualiza el producto en la coleccion.
     */
    public void actulizarProducto(Producto productoActualizado, int productoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, ProductoNoEncontradoException{
        assert productoActualizado != null : "el producto actualizado debe ser distinto de nulo";
        assert user != null : "El usuario debe ser distinto de nulo";

        if(!user.puedeGestionarProductos())
            throw new UsuarioNoAutorizadoException();
        if(productoId < 0)
            throw new IdIncorrectoException();
        Producto producto = getProductoById(productoId);
        if(producto == null)
            throw new ProductoNoEncontradoException();

        producto.updateProducto(productoActualizado);
    };

    /**
     * Se elimina de la colecicon el producto indicada
     * @param idProducto : el id del producto a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si no existe el Id ingresado
     * @throws ProductoNoEncontradoException : Si no se encuentra el producto buscado
     * pre: user != null
     * post: Se elimina el producto de la coleccion
     */
    public void eliminarProducto(int idProducto, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, ProductoNoEncontradoException {
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeGestionarProductos())
            throw new UsuarioNoAutorizadoException();
        if(idProducto < 0)
            throw new IdIncorrectoException();
        Producto producto = getProductoById(idProducto);
        if(producto == null)
            throw new ProductoNoEncontradoException();
        productos.remove(producto);

        assert getProductoById(idProducto) == null : "No se elimino correctamente el producto";

    };

}
