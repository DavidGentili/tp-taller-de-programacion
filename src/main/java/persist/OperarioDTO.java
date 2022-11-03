package persist;

public class OperarioDTO {
    private static enum Tipos {ADMIN, COMUN}
    private static int nroOperario = 0;
    private int id;
    private String nombreApellido;
    private String nombreUsuario;
    private String password;
    private Boolean activo;
    private Tipos tipo;

    /**
     * Retorna el id del operarioDTO
     * @return id del operarioDTO
     */
    public int getId() {
        return id;
    }

    /**
     * Determina el id del operarioDTO
     * @param id : id del operario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna el nombreApellido del operarioDTO
     * @return nombreApellido del operarioDTO
     */
    public String getNombreApellido() {
        return nombreApellido;
    }

    /**
     * Determina el nombreApellido del operarioDTO
     * @param nombreApellido : nombre y apellido del operario
     */
    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    /**
     * Retorna el NombreUsuario del operarioDTO
     * @return NombreUsuario del operarioDTO
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Determina el NombreUsuario del operarioDTO
     * @param nombreUsuario : NombreUsuario del operario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Retorna el Password del operarioDTO
     * @return password del operarioDTO
     */
    public String getPassword() {
        return password;
    }

    /**
     * Determina el Password del operarioDTO
     * @param password : Password del operario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna el Activo del operarioDTO
     * @return Activo del operarioDTO
     */
    public Boolean isActivo() {
        return activo;
    }

    /**
     * Determina el Activo del operarioDTO
     * @param activo : Activo del operario
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Retorna el id del operarioDTO
     * @return id del operarioDTO
     */
    public Tipos getTipo() {
        return tipo;
    }

    /**
     * Determina el tipo del operarioDTO
     * @param tipo : tipo del operario
     */
    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }
}
