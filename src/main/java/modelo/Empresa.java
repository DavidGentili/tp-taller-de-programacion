package modelo;

import modelo.configEmpresa.Operario;
import modelo.gestorEmpresa.GestorEmpresa;

public class Empresa {
    private static Empresa instance = null;
    private GestorEmpresa gestorEmpresa;
    private Operario usuario;

    private Empresa(){
        gestorEmpresa = GestorEmpresa.getInstance();
        usuario = null;
    }

    public static Empresa getInstance(){
        if(instance == null)
            instance = new Empresa();
        return instance;
    }


}
