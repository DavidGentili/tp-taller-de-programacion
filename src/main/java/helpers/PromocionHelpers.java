package helpers;

import enums.FormasDePago;
import exceptions.controlador.ErrorPromocionException;

/**
 * Clase con metodos estaticos, encargada de asistir en las cuestiones relacionadas con las promociones
 */
public class PromocionHelpers {

    public static String[] DIAS = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};


    /**
     * Se encarga de comrpobar si los dias ingresados son correctos
     * @param dias string con dias
     * @throws ErrorPromocionException Si no hay ningun dia en el string ingresado
     */
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

    /**
     * retorna una forma de pago apartir de un string
     * @param pago string que indica el pago
     * @return la forma de pago
     * @throws ErrorPromocionException si no corresponde a ninguna forma de pago
     */
    public static FormasDePago getFormaDePago(String pago) throws ErrorPromocionException {
        try{
            return FormasDePago.valueOf(pago);
        }catch (Exception e){
            throw new ErrorPromocionException("La forma de pago no es correcta");
        }

    }
}
