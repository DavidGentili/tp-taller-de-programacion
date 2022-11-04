package modelo.persist;

import exceptions.ArchivoNoInciliazadoException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IPersistencia<E> {

    /**
     * Se encarga de abrir un archivo de entrada
     * @param name nombre del archivo
     * @throws IOException : Si surge algun error al abrir dicho archivo
     * @throws FileNotFoundException : Si no se encuentra el archivo indicado
     * (FileNotFoundException es parte de IOException, importante a tener en cuenta al momento de capturar la excepcion)
     */
    void openInput(String name) throws IOException, FileNotFoundException;

    /**
     * Se encarga de abrir un archivo de salida
     * @param name nombre del archivo
     * @throws IOException : Si surge algun error al abrir dicho archivo
     * @throws FileNotFoundException : Si no se encuentra el archivo indicado
     * (FileNotFoundException es parte de IOException, importante a tener en cuenta al momento de capturar la excepcion)
     */
    void openOutput(String name) throws IOException, FileNotFoundException;

    /**
     * Se encarga de cerrar el arhcivo de entrada
     * @throws IOException : Si surge un error al intentar cerrar el archivo
     * @throws ArchivoNoInciliazadoException : si el archivo no se encuentra inicializado
     */
    void closeInput() throws IOException, ArchivoNoInciliazadoException;

    /**
     * Se encarga de cerrar el arhcivo de salida
     * @throws IOException : Si surge un error al intentar cerrar el archivo
     * @throws ArchivoNoInciliazadoException : si el archivo no se encuentra inicializado
     */
    void closeOutput() throws IOException, ArchivoNoInciliazadoException;

    /**
     * Se intenta escribir un objeto serializable en un archivo previamiente inicializado
     * @param object : objeto serializable a escribir
     * @throws IOException : Si surge un error al escribir el archivo
     * @throws ArchivoNoInciliazadoException : si el archivo no se encuentra correctamente inicializado
     */
    void writeFile(E object) throws IOException, ArchivoNoInciliazadoException;

    /**
     * Se intenta leer un objeto serializable de un archivo previamiente inicializado
     * @return El objeto leido
     * @throws IOException : Si surge un error al escribir el archivo
     * @throws ClassNotFoundException : Si no se encuentra la clase que se quiere leer
     * @throws ArchivoNoInciliazadoException : si el archivo no se encuentra correctamente inicializado
     */
    E readFile() throws IOException, ClassNotFoundException, ArchivoNoInciliazadoException;

}
