package modelo.configEmpresa;

import persist.OperarioDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class PersistenciaConfiguracion implements Serializable {

    private String nombreLocal;
    private ArrayList<Mozo> mozos;
    private ArrayList<Mesa> mesas;
    private ArrayList<Producto> productos;
    private ArrayList<OperarioDTO> operarios;
    private Sueldo sueldo;

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
}
