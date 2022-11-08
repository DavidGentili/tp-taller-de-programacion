package modelo.configEmpresa;

import exceptions.MesaYaLiberadaException;
import exceptions.MesaYaOcupadaException;

import java.io.Serializable;

public class Mesa implements Serializable {
    public static enum Estados {LIBRE, OCUPADA}
    private int nroMesa;
    private int cantSillas;
    private Estados estado;

    /**
     * Crea una nueva mesa en estado LIBRE
     *
     * @param nroMesa    : el numero de la mesa
     * @param cantSillas : la cantidad de que posee sillas la mesa
     *                   pre: cantSillas > 0 && (nroMesa >= 1 ) ? cantSillas >= 2
     *                   nroMesa >= 0
     */
    public Mesa(int nroMesa, int cantSillas) {
    }

    /**
     *Retorna la cantidad de sillas de la mesa
     * @return Cantidad de sillas de la mesa
     */
    public int getCantSillas() {
        return cantSillas;
    }

    /**
     * Determina la cantidad de sillas de la mesa
     * @param cantSillas : Nueva cantidad de sillas
     */
    protected void setCantSillas(int cantSillas){}

    /**
     * Retorna el estado de la mesa
     *
     * @return estado de la mesa
     */
    public Estados getEstado() {
        return estado;
    }

    /**
     * Se encarga de ocupar la mesa
     * @throws MesaYaOcupadaException : Si la mesa ya se encuentra ocupada
     */
    public void ocuparMesa() throws MesaYaOcupadaException {}

    /**
     * Se encarga de liberar la mesa actual
     * @throws MesaYaLiberadaException : Si la mesa ya se encuentra liberada
     */
    public void liberarMesa() throws MesaYaLiberadaException {}

}

