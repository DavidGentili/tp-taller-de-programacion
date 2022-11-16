package helpers;

import enums.FormasDePago;
import exceptions.controlador.ErrorPromocionException;

public class PromocionHelpers {

    public static String[] DIAS = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};


    public static void checkDias(String dias) throws ErrorPromocionException {
        boolean hayDia = false;
        int i = 0;
        while(!hayDia && i < DIAS.length){
            if(dias.toLowerCase().contains(DIAS[i]))
                hayDia = true;
            i++;
        }
        if(!hayDia)
            throw new ErrorPromocionException("No hay dias correctos de promocion");
    }

    public static FormasDePago getFormaDePago(String pago) throws ErrorPromocionException {
        try{
            return FormasDePago.valueOf(pago);
        }catch (Exception e){
            throw new ErrorPromocionException("La forma de pago no es correcta");
        }

    }
}
