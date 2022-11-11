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

    private Operario USUARIO_BASE = new OperarioAdmin("ADMIN", "ADMIN","ADMIN1234");


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
        file.closeOutput();
    }

    /**
     * Se encarga de recuperar la configuracion de la empresa
     */
    public void recuperarConfiguracion() throws IOException, ArchivoNoInciliazadoException, ClassNotFoundException {
        PersistencaiBin file = new PersistencaiBin();
        file.openInput(Config.ARCHIVO_CONFIGURACION);
        ConfiguracionEmpresaDTO aux = (ConfiguracionEmpresaDTO) file.readFile();
        file.closeInput();
        if(aux.getOperarios().size() == 0)
            aux.getOperarios().add(USUARIO_BASE);
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

    /**
     * Retorna el nombre del local
     * @return nombre del local
     */
    public String getNombreLocal() {return nombreLocal;}

    /**
     * Retorna los mozos almacenados
     * @return mozos almacenados
     */
    public ArrayList<Mozo> getMozos() {
        return mozos;
    }

    /**
     * Retorna las mesas almacenadas
     * @return Mesas almacenadas
     */
    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

    /**
     * Retorna los productos almacenados
     * @return productos almacenados
     */
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    /**
     * Retorna el sueldo almacenado
     * @return sueldo almacenado
     */
    public Sueldo getSueldo() {
        return sueldo;
    }

    /**
     * Retorna los operarios almacenado
     * @return Operararios almacenados
     */
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
