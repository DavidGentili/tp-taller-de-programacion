package modelo.configEmpresa;

import java.io.Serializable;

public class Sueldo implements Serializable {
    private double basico;
    private double bonificacionPorHijo;

    /**
     * Se crea un elemento sueldo con un basico y bonificacion por cantidad de hijos
     * pre: sueldo basico > 0
     *      bonificacionPorHijo > 0
     * @param basico : sueldo basico
     * @param bonificacionPorHijo : bonificacion por hijo
     */
    public Sueldo(double basico, double bonificacionPorHijo){}

    /**
     *Se crea un elemento sueldo con el basico definiendo que no existe bonificacion por hijo, por ende bonificacionPorHijo = 0
     * @param basico : Sueldo basico
     */
    public Sueldo(double basico){}

    /**
     * Retorna el sueldo basico
     * @return Sueldo basico
     */
    public double getBasico() {
        return basico;
    }

    /**
     * Retorna la bonificacion por hijo en porcentaje
     * @return bonificacion por hijo en %
     */
    public double getBonificacionPorHijo(){
        return bonificacionPorHijo;
    }

    /**
     * Determina el sueldo basico del elemento sueldo
     * pre: basico > 0
     * @param basico : sueldo basico
     */
    protected void setBasico(double basico){}

    /**
     * Determina la bonificacion por hijo en %
     * pre: bonificiacionProHijo > 0
     * @param bonificacionPorHijo : bonificacion por hijo en %
     */
    protected void setBonificacionPorHijo(double bonificacionPorHijo){}
}
