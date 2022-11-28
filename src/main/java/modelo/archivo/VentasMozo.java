package modelo.archivo;

import modelo.configEmpresa.Mozo;

/**
 * Clase utilizada para analizar las ventas de un mozo
 */
public class VentasMozo {
    private Mozo mozo;
    private int cont;
    private double acum;

    /**
     * Instancia una elemento con un mozo y la primera venta
     * @param mozo El mozo que realizo la venta
     * @param monto El monto de la venta
     */
    public VentasMozo(Mozo mozo, double monto){
        assert mozo != null : "El mozo no puede ser nulo";
        assert monto >= 0 : "El monto no puede ser negativo";

        this.mozo = mozo;
        acum = monto;
        cont = 1;

        invariante();
        assert this.mozo == mozo : "No se asigno correctamnete el mozo";
        assert acum == monto : "No se asigno correctamente el monto";
        assert cont == 1 : "No se asigno correctamente el contador inicial";
    }

    /**
     * Retorna el mozo
     * @return el mozo
     */
    public Mozo getMozo() {
        return mozo;
    }

    /**
     * Retorna la cantidad de ventas
     * @return cantidad de ventas
     */
    public int getCont() {
        return cont;
    }

    /**
     * Retorna el acumulado de las ventas
     * @return acumulado de las ventas
     */
    public double getAcum() {
        return acum;
    }

    /**
     * Agrega una nueva venta
     * pre: monto >= 0
     * @param monto el monto de la venta
     */
    public void agregaVenta(double monto){
        assert monto >= 0 : "El monto no puede ser negativo";
        int oldCont = cont;
        double oldAcum = acum;

        acum += monto;
        cont++;

        invariante();
        assert acum ==  oldAcum + monto : "No se sumo correctamente el monto";
        assert cont == oldCont + 1 :"No se sumo correctamente el contador";
    }

    /**
     * Retorna el promedio de las ventas
     * @return promedio de ventas
     */
    public double getPromedio(){
        return acum / cont;
    }

    /**
     * Retorna la informacion de ventas del mozo en un string
     * @return informacion de ventas del mozo
     */
    @Override
    public String toString() {
        return String.format("Mozo: %5d %-20s Total : $%6.1f Promedio : $%5.1f", mozo.getId(), mozo.getNombreApellido(), acum, getPromedio());
    }

    private void invariante(){
        assert cont > 0 : "El contador debe ser mayor a 0";
        assert acum > 0 : "El acumulador debe ser mayor a 0";
    }
}
