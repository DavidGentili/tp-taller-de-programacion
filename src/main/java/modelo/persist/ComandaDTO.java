package modelo.persist;

import enums.EstadoComanda;
import modelo.configEmpresa.ConfiguracionEmpresa;
import modelo.configEmpresa.Mesa;
import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.Pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ComandaDTO implements Serializable {

    private GregorianCalendar fecha;
    private int nroMesa;
    private ArrayList<PedidoDTO> pedidos;
    private EstadoComanda estado;

    public ComandaDTO(Comanda comanda){
        this.fecha = comanda.getFecha();
        this.nroMesa = comanda.getMesa().getNroMesa();
        this.pedidos = convertPedidos(comanda.getListaDePedidos());
        this.estado = comanda.getEstado();
    }

    private ArrayList<PedidoDTO> convertPedidos(ArrayList<Pedido> pedidos){
        ArrayList<PedidoDTO> res = new ArrayList<>();
        for(Pedido pedido : pedidos)
            res.add(new PedidoDTO(pedido));
        return res;
    }

    public Comanda getComanda(){
        Mesa mesa = ConfiguracionEmpresa.getInstance().getMesaNroMesa(nroMesa);
        ArrayList<Pedido> aux = new ArrayList<>();
        for(PedidoDTO pedido : pedidos)
            aux.add(pedido.getPedido());
        return new Comanda(mesa, aux, estado, fecha);
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public int getNroMesa() {
        return nroMesa;
    }

    public ArrayList<PedidoDTO> getPedidos() {
        return pedidos;
    }
}
