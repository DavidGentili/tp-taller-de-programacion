package helpers;

import enums.EstadoMozos;
import modelo.configEmpresa.Mozo;

import java.util.ArrayList;

public class MozoHelpers {

    public static int getCantidadDeMozosEnEstado(ArrayList<Mozo> mozos, EstadoMozos estado){
        int cont = 0;
        for(int i = 0 ; i < mozos.size() ; i++)
            cont += mozos.get(i).getEstado() == estado ? 1 : 0;
        return cont;
    }
}
