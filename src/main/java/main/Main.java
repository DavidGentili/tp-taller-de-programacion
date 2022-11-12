package main;


import exceptions.*;
import modelo.configEmpresa.*;

import java.io.IOException;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException, DatosLoginIncorrectosException, MesaYaExistenteException, UsuarioNoAutorizadoException, ProductoYaExistenteException, MozoYaAgregadoException {
        ConfiguracionEmpresa conf = ConfiguracionEmpresa.getInstance();
        conf.guardarConfiguracion();
        conf.recuperarConfiguracion();
        Operario op = conf.login("ADMIN", "ADMIN1234");
        Mesa mesa = new Mesa(5,3);
        Producto prod = new Producto("Papas", 180, 300, 12);
        Mozo mozo = new Mozo("CarlosMartinez", new GregorianCalendar(1997, 04, 04), 3);
        Sueldo sueldo = new Sueldo(95000,5);
        conf.agregarMesa(mesa, op);
        conf.agregarProducto(prod, op);
        conf.agregaMozo(mozo, op);
        conf.setSueldo(sueldo, op);
        System.out.println(conf.getMesas() + "\n" + conf.getMozos() + "\n" + conf.getOperarios(op) + "\n" + conf.getProductos() + "\n" + conf.getSueldo());
    }
}
