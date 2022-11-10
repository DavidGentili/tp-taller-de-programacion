package modelo.configEmpresa;

import exceptions.ArchivoNoInciliazadoException;
import modelo.persist.OperarioDTO;
import modelo.persist.PersistencaiBin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import config.Config;

public class ConfiguracionEmpresaDTO implements Serializable {

    private String nombreLocal;
    private ArrayList<Mozo> mozos;
    private ArrayList<Mesa> mesas;
    private ArrayList<Producto> productos;
    private ArrayList<Operario> operarios;
    private Sueldo sueldo;
    //Variables encargadas de almacenar los valores estaticos de las clases
    private int nroMozos;
    private int nroOperarios;
    private int nroProductos;


    /**
     * Instancia una nueva configuracion de la empresa DTO, para almacenarla apartir de una configuracion empresa
     * pre : configuracion != null
     * @param configuracion : La configuracion que se desee guardar;
     */
    public ConfiguracionEmpresaDTO(ConfiguracionEmpresa configuracion){
        assert configuracion != null : "La configuracion no debe ser nula";

        this.nombreLocal = configuracion.getNombreLocal();
        this.mozos = configuracion.getMozos();
        this.mesas = configuracion.getMesas();
        this.productos = configuracion.getProductos();
        this.operarios = configuracion.getOperarios();
        this.sueldo = configuracion.getSueldo();
        this.nroProductos = Producto.getNroProducto();
        this.nroOperarios = Operario.getNroOperario();
        this.nroMozos = Mozo.getNroMozos();
    }

    /**
     * genera una instancia sin valores, apropiada para recuperar informacion;
     */
    public ConfiguracionEmpresaDTO(){
    }

    /**
     * Se encarga de guarda la configuracion de la empresa.
     */
    public void guardarConfiguaracion() throws IOException, ArchivoNoInciliazadoException {
        PersistencaiBin file = new PersistencaiBin();
        file.openOutput(Config.ARCHIVO_CONFIGURACION);
        file.writeFile(this);
        file.closeInput();
    }

    /**
     * Se encarga de recuperar la configuracion de la empresa
     */
    public void recuperarConfiguracion() throws IOException, ArchivoNoInciliazadoException, ClassNotFoundException {
        PersistencaiBin file = new PersistencaiBin();
        file.openInput(Config.ARCHIVO_CONFIGURACION);
        ConfiguracionEmpresaDTO aux = (ConfiguracionEmpresaDTO) file.readFile();
        this.nombreLocal = aux.getNombreLocal();
        this.mesas = aux.getMesas();
        this.mozos = aux.getMozos();
        this.operarios = aux.getOperarios();
        this.productos = aux.getProductos();
        this.nroMozos = aux.getNroMozos();
        this.sueldo = aux.getSueldo();
        this.nroOperarios = aux.getNroOperarios();
        this.nroProductos = aux.getNroProductos();

    }

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
