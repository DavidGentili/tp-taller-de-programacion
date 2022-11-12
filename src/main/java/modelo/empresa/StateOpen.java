package modelo.empresa;

import enums.EstadoMozos;

import java.util.GregorianCalendar;

public class StateOpen implements StateEmpresa{
    @Override
    public void abrirEmpresa() {

    }

    @Override
    public void CerrarEmpresa() {

    }

    @Override
    public void definirEstadoMozo(int mozoId, EstadoMozos estado) {

    }

    @Override
    public void asignarMozo(int idMozo, int nroMesa, GregorianCalendar fecha) {

    }

    @Override
    public void agregaComanda(int nroMesa) {

    }

    @Override
    public void cerrarComanda(int nroMesa) {

    }

    @Override
    public void agregaPromocion(Promocion promocion) {

    }

    @Override
    public void modificarPromocion(int idPromo) {

    }

    @Override
    public void eliminarPromocion(int idPromo) {

    }

    @Override
    public void activarPromocion(int idPromo) {

    }

    @Override
    public void desactivarPromocion(int idPromo) {

    }

    @Override
    public void login(String userName, String password) {

    }

    @Override
    public void logout() {

    }
}
