package modelo.empresa;

import enums.EstadoMozos;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;

import java.util.GregorianCalendar;

public interface StateEmpresa {

    public void abrirEmpresa();

    public void CerrarEmpresa();

    public void definirEstadoMozo(int mozoId, EstadoMozos estado);

    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha);

    public void agregaComanda(int nroMesa);

    public void cerrarComanda(int nroMesa);

    public void agregaPromocion(Promocion promocion);

    public void modificarPromocion(int idPromo);

    public void eliminarPromocion(int idPromo);

    public void activarPromocion(int idPromo);

    public void desactivarPromocion(int idPromo);

    public void login(String userName, String password);

    public void logout();

}
