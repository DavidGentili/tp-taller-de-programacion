package modelo.archivo;

import modelo.configEmpresa.Mesa;

public class VentasMesa {
    Mesa mesa;
    int cont;
    double acum;

    public VentasMesa(Mesa mesa, double monto){
        assert mesa != null : "La mesa tiene que ser no nula";
        this.mesa = mesa;
        cont = 1;
        acum = monto;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public int getCont() {
        return cont;
    }

    public double getAcum() {
        return acum;
    }

    public void agregaVenta(double monto){
        assert monto >= 0 : "El monto debe ser positivo";
        cont++;
        acum += monto;
    }

    public double getPromedio(){
        return acum / cont;
    }

    @Override
    public String toString() {
        return String.format("Total : %6.1f Promedio : %5.1f", acum, getPromedio());
    }
}
