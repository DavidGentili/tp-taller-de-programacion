package modelo.gestorEmpresa;

import config.Config;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import modelo.Empresa;
import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.persist.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GestorEmpresaDTO implements Serializable{
    private ArrayList<ComandaDTO> comandas;
    private ArrayList<MozoMezaDTO> asignacionMozosMesas;
    private ArrayList<PromocionProductoDTO> promocionesProducto;
    private ArrayList<PromocionTemp> promocionTemporales;
    private String state;
    private int nroPromocion;

    public GestorEmpresaDTO(GestorEmpresa empresa) {
        this.comandas = convertComandas(empresa.getComandas());
        this.asignacionMozosMesas = convertAsignaciones(empresa.getAsignacionMozosMesas());
        this.promocionesProducto = convertPromoProduct(empresa.getPromocionesProducto());
        this.promocionTemporales = empresa.getPromocionesTemporales();
        this.state = empresa.getState() instanceof StateClose ? "close" : "open";
        this.nroPromocion = Promocion.getNroPromociones();
    }

    private ArrayList<ComandaDTO> convertComandas(ArrayList<Comanda> comandas){
        ArrayList<ComandaDTO> res = new ArrayList<>();
        for(Comanda comanda : comandas)
            res.add(new ComandaDTO(comanda));
        return res;
    }

    private ArrayList<MozoMezaDTO> convertAsignaciones(ArrayList<MozoMesa> asignaciones){
        ArrayList <MozoMezaDTO> res = new ArrayList<>();
        for(MozoMesa asignacion : asignaciones)
            res.add(new MozoMezaDTO(asignacion));
        return res;
    }

    private ArrayList<PromocionProductoDTO> convertPromoProduct(ArrayList<PromocionProducto> promociones){
        ArrayList <PromocionProductoDTO> res = new ArrayList<>();
        for(PromocionProducto promocion : promociones)
            res.add(new PromocionProductoDTO(promocion));
        return res;
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
        ArrayList <Comanda> res = new ArrayList<>();
        for(ComandaDTO comanda : comandas)
            res.add( comanda.getComanda());
        return res;
    }

    public ArrayList<MozoMesa> getAsignacionMozosMesas() {
        ArrayList <MozoMesa> res = new ArrayList<>();
        for(MozoMezaDTO asignacion : asignacionMozosMesas)
            res.add( asignacion.getMozoMeza());
        return res;
    }

    public ArrayList<PromocionProducto> getPromocionesProducto() {
        ArrayList <PromocionProducto> res = new ArrayList<>();
        for(PromocionProductoDTO promo : promocionesProducto)
            res.add( promo.getPromocionProducto());
        return res;
    }


    public ArrayList<PromocionTemp> getPromocionTemporales() {
        return promocionTemporales;
    }


    public String getState() {
        return state;
    }

    public int getNroPromocion() {
        return nroPromocion;
    }

    protected StateGestorEmpresa getInstanceState(GestorEmpresa empresa){
        return state.equalsIgnoreCase("open") ? new StateOpen(empresa) : new StateClose(empresa);
    }
}
