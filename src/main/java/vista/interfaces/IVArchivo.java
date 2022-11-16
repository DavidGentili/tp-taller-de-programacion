package vista.interfaces;

import modelo.archivo.Asistencia;
import modelo.archivo.Factura;
import modelo.archivo.VentasMesa;
import modelo.archivo.VentasMozo;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IVArchivo {
    public void setActionListenerEstadisticas(ActionListener a);
    public void setMozoMayorVolumenVentas(VentasMozo mozo);
    public void setMozoMenorVolumenVentas(VentasMozo mozo);
    public void setEstadisticasMozos(ArrayList<VentasMozo> mozos);
    public void setPromedioMesas(ArrayList<VentasMesa> mesas);

    public void cargaAsistencia(ArrayList<Asistencia> asistencias);

    public void actualizaFacturas(ArrayList<Factura> facturas);
}
