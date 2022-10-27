package modelo.empresa;

import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Operario;
import modelo.empresa.Comanda;
import modelo.empresa.MozoMesa;

import java.util.Collection;

public class Empresa {
    private ConfiguracionEmpresa configuracion;
    private Archivo archivo;
    private Collection<Comanda> comandas;
    private Collection<MozoMesa> asignacionMozosMesas;
    private Operario usuario;

}
