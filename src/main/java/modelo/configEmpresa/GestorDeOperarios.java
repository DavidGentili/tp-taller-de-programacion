package modelo.configEmpresa;

import exceptions.*;

import java.util.ArrayList;

public class GestorDeOperarios {
    private ArrayList<Operario> operarios;

    public GestorDeOperarios(){
        this.operarios = new ArrayList<Operario>();
    }

    /**
     * Asigna una lista de operarios al gestor
     * pre: operarios != null
     * @param operarios lista de operarios
     */
    protected void setOperarios(ArrayList<Operario> operarios){
        assert operarios != null : "Los operarios no pueden ser nulos";
        this.operarios = operarios;
        assert this.operarios == operarios : "Los operarios no se asignaron correctamente";
    }

    protected ArrayList<Operario> getOperarios() {
        return operarios;
    }

    public ArrayList<Operario> getOperarios(Operario user) throws UsuarioNoAutorizadoException {
        assert user != null : "El usuario no puede ser nulo";

        if(!user.puedeGestionarOperarios())
            throw new UsuarioNoAutorizadoException();
        return operarios;
    };

    /**
     * Se agrega un operario al registro de la empresa, si el usuario no es admin se emite una exception
     * @param nuevoOperario : el operario que se quiere agregar
     * @param user : El usuario que intenta realizar dicha accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws OperarioYaExistenteException : Si ya existe un operario con ese id en la estructura
     * pre: nuevoOperario != null
     *      user != null
     * post: Se agrega el operario a la coleccion de mesas
     */
    public void agregarOperario(Operario nuevoOperario, Operario user) throws UsuarioNoAutorizadoException, OperarioYaExistenteException {
        assert nuevoOperario != null : "El operario debe ser distinto de nulo";
        assert user != null : "El usuario no debe ser nulo";

        if(!user.puedeGestionarOperarios())
            throw new UsuarioNoAutorizadoException();
        Operario operario = getOperarioByUserName(nuevoOperario.getNombreUsuario());
        if(operario != null)
            throw new OperarioYaExistenteException();
        operarios.add(operario);

        assert getOperarioById(nuevoOperario.getId()) != null : "No se añadio correctamente el operario";
    };

    /**
     * Retorna un operario que corresponde con el id ingresado, si no existe retorna null
     * @param idOperario Id del operario buscado
     * @return Operaririo buscado || null
     */
    private Operario getOperarioById(int idOperario) {
        int i = 0;
        Operario operario = null;
        while(i < operarios.size() && operario != null){
            if(operarios.get(i).getId() == idOperario)
                operario = operarios.get(i);
            i++;
        }
        return operario;
    }

    /**
     * Retorna un operario por nombre de usuario
     * pre: userName != null && !userName.isEmpty() && !userName.isBlank()
     * @param userName : Nombre de usuario
     * @return : Operario con el nombre de usuario solicitado
     */
    private Operario getOperarioByUserName(String userName){
        assert userName != null && !userName.isEmpty() && !userName.isBlank() : "El operario no puede ser nulo, ni blanco ni vacio";
        Operario operario = null;
        int i = 0;
        while(i < operarios.size() && operario == null){
            if(operarios.get(i).getNombreUsuario().equalsIgnoreCase(userName) )
                operario = operarios.get(i);
            i++;
        }
        return operario;
    }

    /**
     * Se actualiza un operario en el sistema
     * @param operarioActualizado : El operario con los valores actualizados
     * @param idOperario : el Id del operario
     * @param user : El usuario que intenta realizar la accion;
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id es incorrecto
     * @throws OperarioNoEncontradoException : Si no se encuentra el operario
     * pre: operarioActualizado != null
     *      user != null
     * post: Se actualiza el operario en la coleccion.
     */
    public void actualizarOperario(Operario operarioActualizado, int idOperario, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, OperarioNoEncontradoException {
        assert operarioActualizado != null : "El operario actualizado debe ser distinto de null";
        assert user != null : "El usuario debe ser distinto de null";

        if(!user.puedeGestionarProductos())
            throw new UsuarioNoAutorizadoException();
        if(idOperario < 0)
            throw new IdIncorrectoException();
        Operario operario = getOperarioById(idOperario);
        if(operario == null)
            throw new OperarioNoEncontradoException();
        operario.updateOperario(operarioActualizado);

    };

    /**
     * Se elimina de la colecicon el producto indicada
     * @param idOperario : el id del operario a eliminar
     * @param user : El usuario que intenta realizar la accion
     * @throws UsuarioNoAutorizadoException : Se emite si el usuario no esta autorizado
     * @throws IdIncorrectoException : Si el id ingresado es incorrecto
     * @throws OperarioNoEncontradoException : Si no se encuentra el operario
     * pre: user != null
     * post: Se elimina el opeario de la coleccion
     */
    public void eliminarOperario(int idOperario, Operario user) throws UsuarioNoAutorizadoException, IdIncorrectoException, OperarioNoEncontradoException {
        assert user != null : "El usuario debe ser no nulo";

        if(!user.puedeGestionarProductos())
            throw new UsuarioNoAutorizadoException();
        if(idOperario < 0)
            throw new IdIncorrectoException();
        Operario operario = getOperarioById(idOperario);
        if(operario == null)
            throw new OperarioNoEncontradoException();
        operarios.remove(operario);

        assert getOperarioById(idOperario) == null : "No se elimino correctamente el operario";
    };

    /**
     * Si el nombre de usuario y contraseña son correctas, retorna el operario correspondiente
     * @param nombreDeUsuario : Nombre de usuario del operario
     * @param password : Contraseña del operario
     * @return El operario correspondiente con el nombre de usuario.
     * @throws DatosLoginIncorrectosException : Si el nombre de usuario o contraseñas son incorrectos
     * pre: nombreDeUsuario != null && nombreDeUsuario != ""
     *      password != null && password != ""
     * post: retorna el operario deseado.
     */
    public Operario login(String nombreDeUsuario, String password) throws DatosLoginIncorrectosException {
        assert nombreDeUsuario != null && !nombreDeUsuario.isEmpty() && !nombreDeUsuario.isBlank() : "El nombre de usuario debe ser distinto de nulo";
        assert password != null && !password.isEmpty() && !password.isBlank() : "La contraseña debe ser distinto de nulo";

        Operario operario = getOperarioByUserName(nombreDeUsuario);
        if(operario == null)
            throw new DatosLoginIncorrectosException();
        if(!operario.matchPassword(password))
            throw new DatosLoginIncorrectosException();
        return operario;

    };
}
