package modelo.configEmpresa;

import java.io.Serializable;

public class Sueldo implements Serializable {
    private double basico;
    private double bonificacionPorHijo;

    /**
     * Se crea un elemento sueldo con un basico y bonificacion por cantidad de hijos
     * pre: sueldo basico > 0
     *      bonificacionPorHijo >= 0
     * @param basico : sueldo basico
     * @param bonificacionPorHijo : bonificacion por hijo
     */
    public Sueldo(double basico, double bonificacionPorHijo){
        assert basico > 0 : "El basico debe ser mayor a 0";
        assert bonificacionPorHijo >= 0 : "La bonificacion por hijo no puede ser negativa";

        this.basico = basico;
        this.bonificacionPorHijo = bonificacionPorHijo;

        assert this.basico == basico : "No se asigno correctamente el basico";
        assert this.bonificacionPorHijo == bonificacionPorHijo : "No se asigno correctamente la bonificacion";
    }

    /**
     *Se crea un elemento sueldo con el basico definiendo que no existe bonificacion por hijo, por ende bonificacionPorHijo = 0
     * @param basico : Sueldo basico
     */
    public Sueldo(double basico){
        assert basico > 0 : "El basico no puede ser negativo";

        this.basico = basico;
        this.bonificacionPorHijo = 0;

        assert this.basico == basico : "No se asigno correctamente el basico";
        assert this.bonificacionPorHijo == 0 : "No se asigno corretamente la bonificacion por hijo";

    }

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
    protected void setBasico(double basico){
        assert basico > 0 : "El basico debe ser mayor a 0";

        this.basico = basico;

        assert this.basico == basico : "No se asigno correctamente el basico";
    }

    /**
     * Determina la bonificacion por hijo en %
     * pre: bonificiacionProHijo > 0
     * @param bonificacionPorHijo : bonificacion por hijo en %
     */
    protected void setBonificacionPorHijo(double bonificacionPorHijo){
        assert bonificacionPorHijo >= 0 : "La bonificacion por hijo no puede ser negativa";

        this.bonificacionPorHijo = bonificacionPorHijo;

        assert this.bonificacionPorHijo == bonificacionPorHijo : "No se asigno correctamente la bonificacion";
    }

    @Override
    public String toString() {
        return String.format("%6.2f %4.1f %%", basico, bonificacionPorHijo);
    }
}
