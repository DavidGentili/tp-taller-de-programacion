package modelo.archivo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import enums.EstadoComanda;
import exceptions.comandas.ComandaAbiertaException;
import exceptions.factura.FacturaYaExistenteException;
import exceptions.persistencia.ArchivoNoInciliazadoException;
import helpers.FechasHelpers;
import modelo.configEmpresa.Mesa;
import modelo.configEmpresa.Mozo;
import modelo.gestorEmpresa.Comanda;
import modelo.gestorEmpresa.MozoMesa;

/**
 * Clase que archiva todos los documentos históricos relacionados al sistema
 * @author
 *
 */
public class Archivo implements Serializable {
	private static Archivo instance = null;
    private ArrayList<Factura> facturas;
    private ArrayList<Comanda> comandas;
    private ArrayList<MozoMesa> asignacionesMozoMesa;
    private ArrayList<Asistencia> registroDeAsistencia;
	

    /**
     * Constructor de la clase Archivo que llama al get instance
     */
    private Archivo() {
		facturas = new ArrayList<>();
		comandas = new ArrayList<>();
		asignacionesMozoMesa = new ArrayList<>();
		registroDeAsistencia = new ArrayList<>();
	}
    
    
    /**
     * PAtron Singleton para el sistema de archivos del sistema
     * @return instancia única de archivos
     */
    public static Archivo getInstance() {
		if(instance == null)
			instance = new Archivo();
		return instance;
	}
    
    
    /**
     * Permite la consulta de la lista de registros históricos
     * @return colección de facturas históricas
     */
    public ArrayList<Factura> getFacturas() {return facturas;}
    
    public Factura getFacturaById(int idFactura){
		assert idFactura >= 0 : "El id no puede ser negativo";
		Factura factura = null;
		int i = 0;
		while(i < facturas.size() && factura == null){
			if(facturas.get(i).getId() == idFactura)
				factura = facturas.get(i);
			i++;
		}
		return factura;
	}

    /**
     * Agrega al listado histórico un nuevo registro
     * @param factura que se desea agregar al listado histórico
     */
	public void agregaFacturas(Factura factura) throws FacturaYaExistenteException {
		assert factura != null : "La factura no puede ser nula";

		Factura actual = getFacturaById(factura.getId());
		if(actual != null)
			throw new FacturaYaExistenteException();
		facturas.add(factura);
	};
	
	/**
	 * Permite la consulta de la lista de registros históricos
	 * @return colección de comandas históricas
	 */
	public ArrayList<Comanda> getComandas() {return comandas;}
	
	
	/**
	 * Agrega al listado histórico un nuevo registro
	 * @param comanda que se desea agregar al listado histórico
	 */
	public void agregaComandas(Comanda comanda) throws ComandaAbiertaException {
		assert comanda != null : "La comanda no puede ser nula";
		if(comanda.getEstado() != EstadoComanda.CERRADA)
			throw new ComandaAbiertaException();
		comandas.add(comanda);
	}
	
	/**
	 * Permite la consulta de la lista de registros históricos
	 * @return colección de registro mozo-meza históricas
	 */
	public ArrayList<MozoMesa> getAsignacionesMozoMesa() {return asignacionesMozoMesa;}
	
	
	/**
	 * Agrega al listado histórico un nuevo registro
	 * @param mozoMesa que se desea agregar al listado histórico
	 */
	public void agregaMozoMesa(MozoMesa mozoMesa) {
		assert mozoMesa != null : "La relacion no puede ser nula";
		asignacionesMozoMesa.add(mozoMesa);
	}

	/**
	 * Agrega un conjunto de relaciones mozo mesa
	 * @param asignaciones lista de asignaciones mozo mesa
	 */
	public void agregaMozoMesa(ArrayList<MozoMesa> asignaciones){
		assert asignaciones != null : "Las asignaciones no pueden ser nulas";
		asignacionesMozoMesa.addAll(asignaciones);
	}


	/**
	 * Permite la consulta de la lista de registros históricos
	 * @return colección de registro de asistencias históricas
	 */
	public ArrayList<Asistencia> getRegistroDeAsistencia() {return registroDeAsistencia;}

	
	/**
	 * Agrega al listado histórico un nuevo registro
	 * @param registroDeAsistencia que se desea agregar al listado histórico
	 */
	public void agregaRegistroDeAsistencia(Asistencia registroDeAsistencia) {
		assert registroDeAsistencia != null : "Las asitencias no pueden ser nulas";
		this.registroDeAsistencia.add(registroDeAsistencia);
	}

	/**
	 * Agrega asistencias al registro apartir de un listado de mozos y una fecha
	 * @param mozos Mozos que se interesa registrar el estado
	 * @param fecha Fecha de los estados
	 */
	public void agregaRegistroDeAsistencia(ArrayList<Mozo> mozos, GregorianCalendar fecha){
		assert mozos != null : "Los mozos no pueden ser nulos";
		assert fecha != null : "la fecha no puede ser nula";
		for (Mozo mozo : mozos){
			Asistencia asistencia = new Asistencia(mozo, mozo.getEstado().toString(), fecha);
			registroDeAsistencia.add(asistencia);
		}
	}

	/**
	 * Retorna una coleccion de VentasMesa, que permiten ver el consumo total y promedio de mesas
	 * @return
	 */
	public HashMap<Integer, VentasMesa>  consumoPromedioXMesa(){
		HashMap<Integer, VentasMesa> ventasMesa= new HashMap<>();
		for(Factura factura : facturas){
			int nroMesa = factura.getMesa().getNroMesa();
			if(ventasMesa.containsKey(nroMesa))
				ventasMesa.get(nroMesa).agregaVenta(factura.getTotal());
			else
				ventasMesa.put(nroMesa, new VentasMesa(factura.getMesa(), factura.getTotal()));
		}
		return ventasMesa;
	}

	/**
	 * Retorna las estadisticas de un mozo total y promedio de ventas
	 * @return coleccion con estadisticas de un mozo
	 */
	public HashMap<Integer, VentasMozo> calculaEstadisticasMozo(){
		HashMap<Integer, VentasMozo> ventasMozos = new HashMap<>();
		for(MozoMesa asignacion : asignacionesMozoMesa){
			recorreFacturas(ventasMozos, asignacion);
		}
		return ventasMozos;
	}

	/**
	 * Recorre las facturas, acumulando las ventas del mozo
	 * @param ventasMozos : coleccion de mozo con sus ventas
	 * @param asignacion : coleccion de asignaciones
	 */
	private void recorreFacturas(HashMap<Integer, VentasMozo> ventasMozos, MozoMesa asignacion){
		Mozo mozo = asignacion.getMozo();
		GregorianCalendar fecha = asignacion.getFecha();
		Mesa mesa = asignacion.getMesa();
		for(Factura factura : facturas){
			if(FechasHelpers.isSameDay(fecha, factura.getFecha()) && factura.getMesa().getNroMesa() == mesa.getNroMesa()){
				if(ventasMozos.containsKey(mozo.getId()))
					ventasMozos.get(mozo.getId()).agregaVenta(factura.getTotal());
				else
					ventasMozos.put(mozo.getId(), new VentasMozo(mozo, factura.getTotal()));
			}
		}
	}

	public VentasMozo getMozoConMayorVolumenDeVentas(){
		VentasMozo maximo = null;
		HashMap<Integer, VentasMozo> ventasMozos = calculaEstadisticasMozo();
		for(Integer idMozo : ventasMozos.keySet()){
			if(maximo == null || maximo.getAcum() < ventasMozos.get(idMozo).getAcum())
				maximo = ventasMozos.get(idMozo);
		}
		return maximo;
	}

	/**
	 * Retorna el mozo con menor volumen de ventas
	 * @return coleccion de ventas mozo, donde se tiene el total y promedio de ventas
	 */
	public VentasMozo getMozoConMenorVolumenDeVentas(){
		VentasMozo minimo = null;
		HashMap<Integer, VentasMozo> ventasMozos = calculaEstadisticasMozo();
		for(Integer idMozo : ventasMozos.keySet()){
			if(minimo == null || minimo.getAcum() > ventasMozos.get(idMozo).getAcum())
				minimo = ventasMozos.get(idMozo);
		}
		return minimo;
	}

	/**
	 * Se encarga de almacenar el archivo
	 * @throws ArchivoNoInciliazadoException : Si no se inicializo el archivo
	 * @throws IOException : Si hubo un problema de lectura
	 */
	public void almacenarArchivo() throws ArchivoNoInciliazadoException, IOException {
		ArchivoDTO file = new ArchivoDTO(this);
		file.almacenarArchivo();
	}

	/**
	 * Recupera la informacion del archivo
	 */
	public void recuperarArchivo(){
		ArchivoDTO file = new ArchivoDTO();
		file.recuperarArchivo();
		facturas = file.getFacturas();
		comandas = file.getComandas();
		asignacionesMozoMesa = file.getAsignacionesMozoMesa();
		registroDeAsistencia = file.getRegistroDeAsistencia();
	}

}
