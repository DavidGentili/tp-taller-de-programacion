package helpers;

import enums.FormasDePago;
import modelo.gestorEmpresa.Pedido;
import modelo.gestorEmpresa.Promocion;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class FacturaHelpers {

    public static String[] DIAS = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};

    public static boolean correspondeDia(String dias){
        GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance();
        int day = now.get(GregorianCalendar.DAY_OF_WEEK);
        return dias.contains(DIAS[ day - 1]);
    }

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
