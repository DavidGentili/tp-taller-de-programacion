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

    /**
     * Instancia un ArchivoDTO con los valores de un Archivo
     * @param archivo
     */
    public ArchivoDTO(Archivo archivo){
        this.facturas = archivo.getFacturas();
        this.comandas = archivo.getComandas();
        this.asignacionesMozoMesa = archivo.getAsignacionesMozoMesa();
        this.registroDeAsistencia = archivo.getRegistroDeAsistencia();
    }

    /**
     * Inicializa el arhicov DTO vacio
     */
    public ArchivoDTO(){
        facturas = new ArrayList<>();
        comandas = new ArrayList<>();
        asignacionesMozoMesa = new ArrayList<>();
        registroDeAsistencia = new ArrayList<>();
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<MozoMesa> getAsignacionesMozoMesa() {
        return asignacionesMozoMesa;
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<Asistencia> getRegistroDeAsistencia() {
        return registroDeAsistencia;
    }

    /**
     * Almacena el archivo
     * @throws ArchivoNoInciliazadoException : Si no se inicializo correctamente el archivo
     * @throws IOException : Si hay un problema de escritura
     */
    public void almacenarArchivo() throws ArchivoNoInciliazadoException, IOException {
        IPersistencia<Serializable> file = new PersistencaiBin();
        file.openOutput(Config.FILE_ARCHIVO);
        file.writeFile(this);
        file.closeOutput();
    }

    /**
     * Recupera el archivoDTO
     */
    public void recuperarArchivo() {
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
