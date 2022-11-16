package controlador;

import exceptions.operarios.UsuarioNoLogueadoException;
import modelo.Empresa;
import modelo.archivo.VentasMesa;
import modelo.archivo.VentasMozo;
import vista.interfaces.IVArchivo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class ControladorArchivo implements ActionListener, Observer {

    IVArchivo vArchivo;

    public ControladorArchivo(IVArchivo vistaEstadisticas){
        vArchivo = vistaEstadisticas;
        Empresa.getInstance().addObserver(this);
        vArchivo.setActionListenerEstadisticas(this);
        actualizaAsistencias();
        actualizaFacturas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try{
            if(command.equals(Commands.CALCULAR_ESTADISTICAS))
                actualizaInformacion();
        } catch(Exception ex){

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        actualizaFacturas();
        actualizaAsistencias();
    }

    private void actualizaInformacion(){
        try{
            actulizaMayorVentas();
            actulizaMenorVentas();
            actualizaEstadisticasMozo();
            actualizaVentasMesa();
        }catch (UsuarioNoLogueadoException e) {
            System.out.println(e);
        }

    }

    private void actulizaMenorVentas() throws UsuarioNoLogueadoException {
        vArchivo.setMozoMenorVolumenVentas(Empresa.getInstance().getMozoConMenorVolumenDeVentas());
    }

    private void actulizaMayorVentas() throws UsuarioNoLogueadoException {
        vArchivo.setMozoMayorVolumenVentas(Empresa.getInstance().getMozoConMayorVolumenDeVentas());
    }

    private void actualizaEstadisticasMozo() throws UsuarioNoLogueadoException {
        HashMap<Integer, VentasMozo> est = Empresa.getInstance().calculaEstadisticasMozo();
        ArrayList<VentasMozo> res= new ArrayList<>(est.values());
        vArchivo.setEstadisticasMozos(res);
    }

    private void actualizaVentasMesa() throws UsuarioNoLogueadoException {
        HashMap<Integer, VentasMesa> est = Empresa.getInstance().calculaPromedioPorMesa();
        ArrayList<VentasMesa> res= new ArrayList<>(est.values());
        vArchivo.setPromedioMesas(res);
    }

    private void actualizaAsistencias(){
        vArchivo.cargaAsistencia(Empresa.getInstance().getAsistencia());
    }

    private void actualizaFacturas(){
        try {
            vArchivo.actualizaFacturas(Empresa.getInstance().getFacturas());
        } catch (UsuarioNoLogueadoException ignored) {

        }
    }


}
