package helpers;

import enums.FormasDePago;
import modelo.gestorEmpresa.Pedido;
import modelo.gestorEmpresa.Promocion;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Clase con metodos estaticos, encargada de asistir en las cuestiones relacionadas con las facturas
 */
public class FacturaHelpers {

    public static String[] DIAS = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};

    /**
     * Retorna si el dia actual consiste con un string de dias
     * @param dias cadena con dias en minuscula
     * @return si corresponde o no el dia actual a la promocion
     */
    public static boolean correspondeDia(String dias){
        GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance();
        int day = now.get(GregorianCalendar.DAY_OF_WEEK);
        return dias.contains(DIAS[ day - 1]);
    }

    /**
     * Retorna si el dia actual consiste con un string de dias
     * @param dias cadena con dias en minuscula
     * @param now el dia que se quiere comparar
     * @return si corresponde o no el dia actual a la promocion
     */
    public static boolean correspondeDia(String dias, GregorianCalendar now){
        int day = now.get(GregorianCalendar.DAY_OF_WEEK);
        return dias.contains(DIAS[ day - 1]);
    }

    /**
     * Retorna una lista de promociones que aplican segun una lista de pedidos, y una forma de pago
     * @param promociones coleccion con todas las promociones
     * @param pedidos lista de pedidos
     * @param formaDePago forma de pagos
     * @return lista de promociones que aplican a la lista de pedidos, la forma de pago y el dia de la fecha
     */
    public static ArrayList<Promocion> getPromocionesAplicadas(ArrayList<Promocion> promociones, ArrayList<Pedido> pedidos, FormasDePago formaDePago){
        ArrayList<Promocion> aplicables = new ArrayList<Promocion>();
        for(Promocion promocion : promociones){
            if(promocion.aplicaPromocion(pedidos, formaDePago))
                aplicables.add(promocion);
        }
        for(Promocion promocion : aplicables){
            if(!promocion.isAcumulable() && aplicables.size() > 1)
                aplicables.remove(promocion);
        }
        return aplicables;

    }
}
