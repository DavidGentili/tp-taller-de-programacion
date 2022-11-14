package modelo.gestorEmpresa;

import config.Config;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import modelo.Empresa;
import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.persist.IPersistencia;
import modelo.persist.PersistencaiBin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GestorEmpresaDTO implements Serializable{
    private ArrayList<Comanda> comandas;
    private ArrayList<MozoMesa> asignacionMozosMesas;
    private ArrayList<PromocionProducto> promocionesProducto;
    private ArrayList<PromocionTemp> promocionTemporales;
    private String state;
    private int nroPromocion;

    public GestorEmpresaDTO(GestorEmpresa empresa) {
        this.comandas = empresa.getComandas();
        this.asignacionMozosMesas = empresa.getAsignacionMozosMesas();
        this.promocionesProducto = empresa.getPromocionesProducto();
        this.promocionTemporales = empresa.getPromocionesTemporales();
        this.state = empresa.getState() instanceof StateClose ? "close" : "open";
        this.nroPromocion = Promocion.getNroPromociones();
    }

    public GestorEmpresaDTO(){
        this.comandas = new ArrayList<>();
        this.asignacionMozosMesas =new ArrayList<>();
        this.promocionesProducto = new ArrayList<>();
        this.promocionTemporales = new ArrayList<>();
    }

    public void almacenarDatos() throws IOException, ArchivoNoInciliazadoException {
        IPersistencia<Serializable> file = new PersistencaiBin();
        file.openOutput(Config.ARCHIVO_GESTOR_EMPRESA);
        file.writeFile(this);
        file.closeOutput();
    }

    public void recuperarDatos() throws IOException, ArchivoNoInciliazadoException, ClassNotFoundException {
        IPersistencia<Serializable> file = new PersistencaiBin();
        file.openInput(Config.ARCHIVO_GESTOR_EMPRESA);
        GestorEmpresaDTO aux = (GestorEmpresaDTO) file.readFile();
        this.comandas = aux.comandas;
        this.promocionesProducto = aux.promocionesProducto;
        this.promocionTemporales = aux.promocionTemporales;
        this.asignacionMozosMesas = aux.asignacionMozosMesas;
        this.state = aux.state;
        this.nroPromocion = aux.nroPromocion;
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(ArrayList<Comanda> comandas) {
        this.comandas = comandas;
    }

    public ArrayList<MozoMesa> getAsignacionMozosMesas() {
        return asignacionMozosMesas;
    }

    public void setAsignacionMozosMesas(ArrayList<MozoMesa> asignacionMozosMesas) {
        this.asignacionMozosMesas = asignacionMozosMesas;
    }

    public ArrayList<PromocionProducto> getPromocionesProducto() {
        return promocionesProducto;
    }

    public void setPromocionesProducto(ArrayList<PromocionProducto> promocionesProducto) {
        this.promocionesProducto = promocionesProducto;
    }

    public ArrayList<PromocionTemp> getPromocionTemporales() {
        return promocionTemporales;
    }

    public void setPromocionTemporales(ArrayList<PromocionTemp> promocionTemporales) {
        this.promocionTemporales = promocionTemporales;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNroPromocion() {
        return nroPromocion;
    }

    public void setNroPromocion(int nroPromocion) {
        this.nroPromocion = nroPromocion;
    }

    protected StateGestorEmpresa getInstanceState(GestorEmpresa empresa){
        return state.equalsIgnoreCase("open") ? new StateOpen(empresa) : new StateClose(empresa);
    }
}
