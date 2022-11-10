package modelo.configEmpresa;

import exceptions.IdIncorrectoException;
import exceptions.MesaNoEncontradaException;
import exceptions.MesaYaExistenteException;
import exceptions.UsuarioNoAutorizadoException;

import java.util.ArrayList;

public class GestorDeMesas {
    private ArrayList<Mesa> mesas;

    public GestorDeMesas(){
        this.mesas = new ArrayList<Mesa>();
    }

    /**
     * Asigna una lista de mesas al gesto de mesas
     * pre: mesas != null
     * @param mesas lista de mesas a signar
     */
    protected void setMesas(ArrayList<Mesa> mesas){
        assert mesas != null : "Las mesas no deben ser nulas";
        this.mesas = mesas;
        assert this.mesas == mesas : "No se asignaron correctamente las mesas";
    }

    /**
     * Retorna las mesas del sistema
     * @return Las mesas del sistema
     */
    public ArrayList<Mesa> getMesas() {
        return mesas;
    };

    /**
     * Retorna la mesa correspondiente al id ingresado, en caso de que no exista una meza con dicho id retorna null
     * pre: nroMesa >= 0;
     * @param nroMesa : numero de la mesa deseado
     * @return la mesa correspondiente al id ingresado
     */
    public Mesa getMesaNroMesa(int nroMesa) {
        assert nroMesa >= 0 : "El nro de mesa no puede ser negativo";
        Mesa mesa = null;
        int i = 0;
        while(mesa == null && i < mesas.size())
            mesa = mesas.get(i).getNroMesa() == nroMesa ? mesas.get(i) : null;
        return mesa;
    };

    /**
     * Se agrega una mesa al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevaMesa : La mesa que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws MesaYaExistenteException : Si el numero de mesa ya existe
     * pre: nuevaMesa != null
     *      user != null
     * post: Se agrega la meza a la coleccion de mesas
     */
    public void agregarMesa(Mesa nuevaMesa, Operario user) throws UsuarioNoAutorizadoException, MesaYaExistenteException {
        assert nuevaMesa != null : "La nueva mesa no puede ser nula";
        assert user != null : "El usuario tiene que ser no nulo";

        if(!user.puedeGestionarMesas())
            throw new UsuarioNoAutorizadoException();
        if(getMesaNroMesa(nuevaMesa.getNroMesa()) != null)
            throw new MesaYaExistenteException();
        mesas.add(nuevaMesa);

        assert getMesaNroMesa(nuevaMesa.getNroMesa()) == nuevaMesa : "No se agrego correctamente la nueva mesa";
    };

    /**
     * Se actualiza la mesa en el sistema
     * @param mesaActualizada : La mesa con los valores actualizados
     * @param nroMesa : el numero de la mesa
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws MesaNoEncontradaException : si no se encuentra la mesa en la coleccion
     * pre: mesaAcutalizada != null
     *      user != null
     * post: Se actualiza la mesa en la coleccion.
     */
    public void actulizarMesa(Mesa mesaActualizada, int nroMesa, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MesaNoEncontradaException{
        assert mesaActualizada != null : "La mesa actualizada no puede ser nula";
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeGestionarMesas())
            throw new UsuarioNoAutorizadoException();
        if(nroMesa < 0)
            throw new IdIncorrectoException();
        Mesa mesa = getMesaNroMesa(nroMesa);
        if(mesa == null)
            throw new MesaNoEncontradaException();
        mesa.updateMesa(mesaActualizada);

        assert mesa.getCantSillas() == mesaActualizada.getCantSillas() : "No se actualizo correctamente la mesa";
    };

    /**
     * Se elimina de la colecicon la mesa indicada
     * @param nroMesa : el numero de la mesa a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id no es correcto
     * @throws MesaNoEncontradaException : Si la mesa a eliminar no existe en la coleccion
     * pre: user != null
     * post: Se elimina la mesa de la coleccion
     */
    public void eliminarMesa(int nroMesa, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MesaNoEncontradaException {
        assert user != null : "El usuario debe ser no nulo";

        if(!user.puedeGestionarMesas())
            throw new UsuarioNoAutorizadoException();
        if(nroMesa < 0)
            throw new IdIncorrectoException();
        Mesa mesa = getMesaNroMesa(nroMesa);
        if(mesa == null)
            throw new MesaNoEncontradaException();
        mesas.remove(mesa);

        assert getMesaNroMesa(nroMesa) == null : "No se elimino correctamente la mesa";
    };

}
