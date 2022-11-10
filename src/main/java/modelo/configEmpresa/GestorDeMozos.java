package modelo.configEmpresa;

import exceptions.IdIncorrectoException;
import exceptions.MozoNoEncontradoException;
import exceptions.MozoYaAgregadoException;
import exceptions.UsuarioNoAutorizadoException;

import java.util.ArrayList;

public class GestorDeMozos {
    private ArrayList<Mozo> mozos;

    public GestorDeMozos(){
        mozos = new ArrayList<Mozo>();
    }

    /**
     * Asigna un nuevo conjunto de mozos al gestor
     * pre: mozos != null
     * @param mozos : nuevo arreglo de mozos
     */
    protected void setMozos(ArrayList<Mozo> mozos){
        assert mozos != null : "Lo mozos no pueden ser nulos";
        this.mozos = mozos;
        assert this.mozos == mozos : "No se asigno correctamente a los mozos";
    }

    /**
     * Se encarga de agregar un nuevo mozo a los registros de la empresa, si el usuario no es admin
     * se emite una excepcion
     * @param nuevoMozo : El nuevo mozo que se desea agregar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws MozoYaAgregadoException : Si el mozo ya se encuentra agregado en la coleccion
     * pre: el nuevo mozo debe ser distinto de null
     *      user != null
     * post: se añadira un nuevo mozo a la coleccion
     */
    public void agregaMozo(Mozo nuevoMozo, Operario user) throws UsuarioNoAutorizadoException, MozoYaAgregadoException {
        assert mozos != null : "El mozo no puede ser nulo";
        assert user != null : "El ususarion o puede ser nulo";

        if(!user.puedeGestionarMozos())
            throw new UsuarioNoAutorizadoException();
        if(this.getMozoById(nuevoMozo.getId()) != null)

            throw new MozoYaAgregadoException();
        mozos.add(nuevoMozo);

        assert nuevoMozo == getMozoById(nuevoMozo.getId()) : "El mozo no fue añadido correctamente a la coleccion";
    }

    /**
     * Se encarga de actualizar un mozo, si el usuario no es admin se emite una excepcion
     * @param mozoActualizado : El mozo que se desea actualizar
     * @param mozoId : El usuario que intenta realizar la accion
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * pre: mozoActualizado no debe ser nulo
     *      user != null
     * post: el mozo del sistema tomara los valores de mozoActulizado,
     */
    public void actualizarMozo(Mozo mozoActualizado, int mozoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MozoNoEncontradoException {
        assert mozoActualizado != null : "El mozo con los datos a actualizar no debe ser nulo";
        assert user != null : "El usuario no debe ser nulo";

        if(!user.puedeGestionarMozos())
            throw new UsuarioNoAutorizadoException();
        if(mozoId < 0)
            throw new IdIncorrectoException();
        Mozo mozo = this.getMozoById(mozoId);
        if(mozo == null)
            throw new MozoNoEncontradoException();
        mozo.updateMozo(mozoActualizado);

    };

    /**
     * Se encarga de eliminar un mozo segun su Id
     * @param mozoId : El id del mozo a eleminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws MozoNoEncontradoException : Si el mozo no existe en la coleccion indicada
     * pre: user != null
     * post: Se eliminara el mozo con el id ingresado de la coleccion
     */
    public void eliminaMozo(int mozoId, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, MozoNoEncontradoException {
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeGestionarMozos())
            throw new UsuarioNoAutorizadoException();
        if(mozoId < 0)
            throw new IdIncorrectoException();
        Mozo mozo = this.getMozoById(mozoId);
        if(mozo == null)
            throw new MozoNoEncontradoException();
        mozos.remove(mozo);

        assert getMozoById(mozoId) == null : "No se elimino el mozo correctamente";
    };


    /**
     * Retorna los mozos del sistema
     * @return Los mozos del sistema
     */
    public ArrayList<Mozo> getMozos() {
        return mozos;
    };

    /**
     * Retorna el mozo correspondiente al id ingresado, en caso de que no exista dicho id arroja una excepcion
     * pre : id >= 0
     * @param mozoId : Id del mozo deseado
     * @return el mozo correspondiente al id ingresado
     */
    public Mozo getMozoById(int mozoId){
        assert mozoId >= 0 : "El id no puede ser negativo";
        Mozo mozo = null;
        int i = 0;
        while(mozo == null && i < mozos.size())
            mozo = mozos.get(i).getId() == mozoId ? mozos.get(i) : null;
        return mozo;
    }
}
