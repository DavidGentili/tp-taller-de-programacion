package modelo.empresa;

import java.util.Collection;

import modelo.configEmpresa.Producto;

public class PromocionProducto extends Promocion {

	private Collection<Producto> productos;
	private boolean dosporuno;
	private boolean dtoporcant;
	
	
	public PromocionProducto(boolean dosporuno, boolean dtoporcant) {
		super();
	}
	
	public void AgregaPorducto(Producto producto) {
		
	}

	public Collection<Producto> getProductos() {
		return productos;
	}

	public boolean isDosporuno() {
		return dosporuno;
	}

	public boolean isDtoporcant() {
		return dtoporcant;
	}
	
	
	
	
}
