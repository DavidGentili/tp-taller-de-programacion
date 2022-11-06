package modelo.empresa;

import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.configEmpresa.Operario;
import modelo.empresa.Comanda;
import modelo.empresa.MozoMesa;

import java.util.Collection;
import java.util.Date;

import exceptions.IdIncorrectoException;

public class Empresa {
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private Collection<Comanda> comandas;
    private Collection<MozoMesa> asignacionMozosMesas;
    private Collection<Promocion> promociones;
    private Operario usuario; 
    
    public void AsignarMozo(Mozo mozo,Mesa mesa,Date dia) {
    	
    }
    
    public void NuevaComanda(Mesa mesa) {
    	
    }
    
    public void NuevaPromocion(enum dias,boolean dosporuno, boolean dtoporcant){
    	
    }
    
    public void NuevaPromocion(enum dias,String nombre,enum formapago,int porcentajedto,boolean acumulable){
    	
    }
    
    public void CerrarComanda(Comanda comanda) {
    	
    }
    public void Desactivarpromocion(int idpromo) throws IdIncorrectoException {
    	
    }
    
    public void ActivarPromocion(int idpromo) throws IdIncorrectoException {
   	 
    }

}
