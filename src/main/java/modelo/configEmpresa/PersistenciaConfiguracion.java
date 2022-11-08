package modelo.configEmpresa;

import modelo.persist.OperarioDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class PersistenciaConfiguracion implements Serializable {

    private String nombreLocal;
    private ArrayList<Mozo> mozos;
    private ArrayList<Mesa> mesas;
    private ArrayList<Producto> productos;
    private ArrayList<OperarioDTO> operarios;
    private Sueldo sueldo;
    //Variables encargadas de almacenar los valores estaticos de las clases
    private int nroMozos;
    private int nroOperarios;
    private int nroProductos;


    public PersistenciaConfiguracion(String nombreLocal, ArrayList<Mozo> mozos, ArrayList<Mesa> mesas, ArrayList<Producto> productos, ArrayList<Operario> operarios, Sueldo sueldo){}

    public PersistenciaConfiguracion(){}

    public void guardarConfiguaracion(){}

    public void recuperarConfiguracion(){}

    public String getNombreLocal() {return nombreLocal;}

    public ArrayList<Mozo> getMozos() {
        return mozos;
    }

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Sueldo getSueldo() {
        return sueldo;
    }

    public ArrayList<Operario> getOperarios() {
        return null;
    }

    /**
     * Retorna el numero de mozos (numero usado para obtenr id) almacenado
     * @return Numero de mozos
     */
    public int getNroMozos() {return nroMozos;}

    /**
     * Retorna el numero de productos (numero usado para obtenr id) almacenado
     * @return Numero de productos
     */
    public int getNroProductos(){ return nroProductos;}

    /**
     * Retorna el numero de operarios (numero usado para obtenr id) almacenado
     * @return Numero de operarios
     */
    public int getNroOperarios(){return nroOperarios;}
}
