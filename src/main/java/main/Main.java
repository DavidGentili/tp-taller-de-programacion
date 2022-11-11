package main;


import exceptions.ArchivoNoInciliazadoException;
import exceptions.DatosLoginIncorrectosException;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Operario;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException, DatosLoginIncorrectosException {
        ConfiguracionEmpresa conf = ConfiguracionEmpresa.getInstance();
        conf.guardarConfiguracion();
        conf.recuperarConfiguracion();
        Operario op = conf.login("ADMIN", "ADMIN1234");
        System.out.println(op);
    }
}
