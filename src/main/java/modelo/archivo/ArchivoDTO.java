package modelo.archivo;


import config.Config;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.MozoMesa;
import modelo.persist.IPersistencia;
import modelo.persist.PersistencaiBin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ArchivoDTO implements Serializable{
    private ArrayList<Factura> facturas;
    private ArrayList<Comanda> comandas;
    private ArrayList<MozoMesa> asignacionesMozoMesa;
    private ArrayList<Asistencia> registroDeAsistencia;

    public ArchivoDTO(Archivo archivo){
        this.facturas = archivo.getFacturas();
        this.comandas = archivo.getComandas();
        this.asignacionesMozoMesa = archivo.getAsignacionesMozoMesa();
        this.registroDeAsistencia = archivo.getRegistroDeAsistencia();
    }

    public ArchivoDTO(){
        facturas = new ArrayList<>();
        comandas = new ArrayList<>();
        asignacionesMozoMesa = new ArrayList<>();
        registroDeAsistencia = new ArrayList<>();
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    public ArrayList<MozoMesa> getAsignacionesMozoMesa() {
        return asignacionesMozoMesa;
    }

    public ArrayList<Asistencia> getRegistroDeAsistencia() {
        return registroDeAsistencia;
    }

    public void almacenarArchivo() throws ArchivoNoInciliazadoException, IOException {
        IPersistencia<Serializable> file = new PersistencaiBin();
        file.openOutput(Config.FILE_ARCHIVO);
        file.writeFile(this);
        file.closeOutput();
    }

    public void recuperarArchivo() throws ArchivoNoInciliazadoException, IOException, ClassNotFoundException {
        try {
            IPersistencia file = new PersistencaiBin();
            file.openInput(Config.FILE_ARCHIVO);
            ArchivoDTO pers = (ArchivoDTO) file.readFile();
            facturas = pers.getFacturas();
            comandas = pers.getComandas();
            asignacionesMozoMesa = pers.getAsignacionesMozoMesa();
            registroDeAsistencia = pers.getRegistroDeAsistencia();
        } catch(ArchivoNoInciliazadoException | IOException | ClassNotFoundException e){
            facturas = new ArrayList<>();
            comandas = new ArrayList<>();
            asignacionesMozoMesa = new ArrayList<>();
            registroDeAsistencia = new ArrayList<>();
        }
    }
}
