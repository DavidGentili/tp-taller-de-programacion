package modelo.configEmpresa;

import exceptions.mesas.MesaYaLiberadaException;
import exceptions.mesas.MesaYaOcupadaException;

import enums.EstadoMesas;

import java.io.Serializable;

public class Mesa implements Serializable, Cloneable{
    private int nroMesa;
    private int cantSillas;
    private EstadoMesas estado;

    /**
     * Crea una nueva mesa en estado LIBRE
     *
     * @param nroMesa    : el numero de la mesa
     * @param cantSillas : la cantidad de que posee sillas la mesa
     *                   pre: cantSillas > 0 && (nroMesa >= 1 ) ? cantSillas >= 2
     *                   nroMesa >= 0
     */
    public Mesa(int nroMesa, int cantSillas) {
        assert nroMesa >= 0 : "El numero de mesa debe ser mayor o igual a 0";
        assert ((nroMesa > 0) ? cantSillas >= 2 : cantSillas > 0) : "La cantidad de sillas es incorrecta";

        this.nroMesa = nroMesa;
        this.cantSillas = cantSillas;
        this.estado = EstadoMesas.LIBRE;

        assert this.nroMesa == nroMesa : "No se asigno correctamente el numero de mesa";
        assert this.cantSillas == cantSillas : "No se asigno correctamente la cantidad de sillas";
        assert  this.estado == EstadoMesas.LIBRE : "No se asigno correctamente el estado inicial de la mesa";
    }

    /**
     * Retorna el numero de la mesa
     * @return Numero de la mesa
     */
    public int getNroMesa() {
        return nroMesa;
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
     * pre: (nroMesa > 0) ? cantSillas > 2 : cantSillas > 0;
     * post this.cantSillas == cantSillas
     * @param cantSillas : Nueva cantidad de sillas
     *
     */
    protected void setCantSillas(int cantSillas){
        assert ((nroMesa > 0) ? cantSillas >= 2 : cantSillas > 0) : "La cantidad de sillas es incorrecta";

        this.cantSillas = cantSillas;

        assert this.cantSillas == cantSillas : "No se asigno correctamente la cantidad de sillas";
    }

    /**
     * Retorna el estado de la mesa
     *
     * @return estado de la mesa
     */
    public EstadoMesas getEstado() {
        return estado;
    }

    /**
     * Se encarga de ocupar la mesa
     * @throws MesaYaOcupadaException : Si la mesa ya se encuentra ocupada
     */
    public void ocuparMesa() throws MesaYaOcupadaException {
        if(this.estado == EstadoMesas.OCUPADA)
            throw new MesaYaOcupadaException("La mesa se encuentra ocupada");
        this.estado = EstadoMesas.OCUPADA;

        assert this.estado == EstadoMesas.OCUPADA : "La mesa no se ocupo correctamente";
    }

    /**
     * Se encarga de liberar la mesa actual
     * @throws MesaYaLiberadaException : Si la mesa ya se encuentra liberada
     */
    public void liberarMesa() throws MesaYaLiberadaException {
        if(this.estado == EstadoMesas.LIBRE)
            throw new MesaYaLiberadaException("La mesa se encuentra libre");
        this.estado = EstadoMesas.LIBRE;

        assert this.estado == EstadoMesas.LIBRE : "La mesa no se libero correctamente";
    }

    protected void updateMesa(Mesa other){
        this.cantSillas = other.cantSillas;
    }

    @Override
    public String toString() {
        return String.format("%4d %2d %-10s", nroMesa, cantSillas, estado);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

