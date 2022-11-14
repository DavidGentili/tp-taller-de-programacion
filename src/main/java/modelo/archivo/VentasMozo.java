package modelo.archivo;

import modelo.configEmpresa.Mozo;

public class VentasMozo {
    private Mozo mozo;
    private int cont;
    private double acum;

    public VentasMozo(Mozo mozo, double monto){
        assert mozo != null : "El mozo no puede ser nulo";
        assert monto >= 0 : "El monto no puede ser negativo";
        this.mozo = mozo;
        acum = monto;
        cont = 1;
    }

    public Mozo getMozo() {
        return mozo;
    }

    public int getCont() {
        return cont;
    }

    public double getAcum() {
        return acum;
    }

    public void agregaVenta(double monto){
        assert monto >= 0 : "El monto no puede ser negativo";
        acum += monto;
        cont++;
    }

    public double getPromedio(){
        return acum / cont;
    }

    @Override
    public String toString() {
        return String.format("Total : %6.1f Promedio : %5.1f", acum, getPromedio());
    }
}
