package main;


import exceptions.IdIncorrectoException;
import exceptions.gestorEmpresa.EmpresaAbiertaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.GestorEmpresa;
import modelo.gestorEmpresa.MozoMesa;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException, MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, MesaNoEncontradaException, MozoNoActivoException, MesaYaOcupadaException {
        GestorEmpresa empresa = GestorEmpresa.getInstance();
        ConfiguracionEmpresa config = ConfiguracionEmpresa.getInstance();
        config.recuperarConfiguracion();
        empresa.recuperarEmpresa();


    }
}
