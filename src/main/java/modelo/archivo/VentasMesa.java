package modelo.archivo;

import modelo.configEmpresa.Mesa;

/**
 * Clase utilizada para representar las ventas de una mesa
 */
public class VentasMesa {
    Mesa mesa;
    int cont;
    double acum;

    /**
     * Inicializa el elemento con una mesa y el monto de la primer venta
     * @param mesa Mesa correspondiente
     * @param monto Monto de primera venta
     */
    public VentasMesa(Mesa mesa, double monto){
        assert mesa != null : "La mesa tiene que ser no nula";

        this.mesa = mesa;
        cont = 1;
        acum = monto;

        invariante();
        assert this.mesa == mesa : "No se asigno correctamente la mesa";
        assert acum == monto : "No se asigno correctamente el monto";
        assert cont == 1 : "No se asigno correctamente el contador inicial";
    }

    /**
     * Retorna la mesa
     * @return mesa
     */
    public Mesa getMesa() {
        return mesa;
    }

    /**
     * Retonra la cantidad de ventas
     * @return cantidad de ventas
     */
    public int getCont() {
        return cont;
    }

    /**
     * Retorna el acumulado de ventas
     * @return acumulado de ventas
     */
    public double getAcum() {
        return acum;
    }

    /**
     * Agrega una venta
     * @param monto monto de la venta
     */
    public void agregaVenta(double monto){
        assert monto >= 0 : "El monto debe ser positivo";

        int oldCont = cont;
        double oldAcum = acum;

        cont++;
        acum += monto;

        invariante();
        assert acum ==  oldAcum + monto : "No se sumo correctamente el monto";
        assert cont == oldCont + 1 :"No se sumo correctamente el contador";
    }

    /**
     * Obtiene el promedio de ventas
     * @return
     */
    public double getPromedio(){
        return acum / cont;
    }

    /**
     * Retorna la informacion de la venta de la mesa en un string
     * @return informacion de la venta de la mesa en string
     */
    @Override
    public String toString() {
        return String.format("Nro. mesa: %4d Total: $%6.1f Promedio: $%5.1f", mesa.getNroMesa(), acum, getPromedio());
    }

    private void invariante(){
        assert cont > 0 : "El contador debe ser mayor a 0";
        assert acum > 0 : "El acumulador debe ser mayor a 0";
    }
}
