package main;


import exceptions.IdIncorrectoException;
import exceptions.controlador.ErrorAlAgregarMozoException;
import exceptions.factura.FacturaYaExistenteException;
import exceptions.gestorEmpresa.EmpresaAbiertaException;
import exceptions.mesas.MesaNoEncontradaException;
import exceptions.mesas.MesaYaExistenteException;
import exceptions.mesas.MesaYaOcupadaException;
import exceptions.mozos.MozoNoActivoException;
import exceptions.mozos.MozoNoEncontradoException;
import exceptions.mozos.MozoYaAgregadoException;
import exceptions.operarios.*;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import exceptions.productos.ProductoYaExistenteException;
import helpers.MozoHelpers;
import modelo.archivo.Archivo;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Operario;
import modelo.gestorEmpresa.*;

import java.io.IOException;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException, MozoNoEncontradoException, IdIncorrectoException, EmpresaAbiertaException, MesaNoEncontradaException, MozoNoActivoException, MesaYaOcupadaException, ProductoYaExistenteException, UsuarioNoAutorizadoException, OperarioInactivoException, DatosLoginIncorrectosException, MesaYaExistenteException, MozoYaAgregadoException, OperarioNoEncontradoException, ContraseniaIncorrectaException, ErrorAlAgregarMozoException, FacturaYaExistenteException {
        GestorEmpresa empresa = GestorEmpresa.getInstance();
        ConfiguracionEmpresa config = ConfiguracionEmpresa.getInstance();
        Archivo archivo = Archivo.getInstance();
        config.recuperarConfiguracion();
        empresa.recuperarEmpresa();
        archivo.recuperarArchivo();
        Operario user = config.login("ADMIN", "ADMIN1234");
        user.cambiarContrasenia("ADMIN1234", "Admin1");
        CargaDatos.cargaMozos(user);
        CargaDatos.cargaMesas(user);
        CargaDatos.cargaProductos(user);
    }


}
