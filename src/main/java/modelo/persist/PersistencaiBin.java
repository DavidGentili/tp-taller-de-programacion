package modelo.persist;

import exceptions.ArchivoNoInciliazadoException;

import java.io.*;

public class PersistencaiBin implements IPersistencia<Serializable>{

    private FileInputStream fileInput;
    private FileOutputStream fileOutput;
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;

    @Override
    public void openInput(String name) throws IOException, FileNotFoundException {
        fileInput = new FileInputStream(name);
        input = new ObjectInputStream(fileInput);
    }

    @Override
    public void openOutput(String name) throws IOException {
        fileOutput = new FileOutputStream(name);
        output = new ObjectOutputStream(fileOutput);
    }

    @Override
    public void closeInput() throws IOException, ArchivoNoInciliazadoException {
        if(input == null)
            throw new ArchivoNoInciliazadoException();
        input.close();
    }

    @Override
    public void closeOutput() throws IOException, ArchivoNoInciliazadoException {
        if(output == null)
            throw new ArchivoNoInciliazadoException();
        output.close();
    }

    @Override
    public void writeFile(Serializable object) throws IOException, ArchivoNoInciliazadoException {
        if(output == null)
            throw new ArchivoNoInciliazadoException();
        output.writeObject(object);
    }

    @Override
    public Serializable readFile() throws IOException, ClassNotFoundException, ArchivoNoInciliazadoException {
        Serializable response = null;
        if(input == null)
            throw new ArchivoNoInciliazadoException();
        response = (Serializable) input.readObject();
        return response;
    }
}
