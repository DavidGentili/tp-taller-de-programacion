package modelo.persist;

import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.MozoMesa;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class MozoMezaDTO implements Serializable {
    private int nroMesa;
    private int idMozo;
    private GregorianCalendar fecha;

    public MozoMezaDTO(MozoMesa original){
        this.idMozo = original.getMozo().getId();
        this.nroMesa = original.getMesa().getNroMesa();
        this.fecha = original.getFecha();
    }

    public int getNroMesa() {
        return nroMesa;
    }

    public void setNroMesa(int nroMesa) {
        this.nroMesa = nroMesa;
    }

    public int getIdMozo() {
        return idMozo;
    }

    public void setIdMozo(int idMozo) {
        this.idMozo = idMozo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public MozoMesa getMozoMeza(){
        ConfiguracionEmpresa config = ConfiguracionEmpresa.getInstance();
        Mesa mesa = config.getMesaNroMesa(nroMesa);
        Mozo mozo = config.getMozoById(idMozo);
        return new MozoMesa(fecha, mozo, mesa);
    }
}
