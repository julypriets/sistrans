package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que se encarga de modelar el concepto de Orden en Superandes.
 * @author s.lemus
 *
 */
public class Orden implements VOOrden{
	
	/**
	 * Identificador único del pedido
	 */
	private long id;
	
	/**
	 * Precio del pedido
	 */
	private Double precio;
	
	/**
	 * Fecha en la que se espera que llegue el pedido al cliente
	 */
	private Timestamp fechaEsperada;
	
	/**
	 * Fecha en la que llega el pedido al cliente
	 */
	private Timestamp fechaLlegada;
	
	/**
	 * Estado del pedio (Pendiente o Entregado)
	 */
	private String estado;
	
	/**
	 * Calificación del pedidio
	 */
	private Double calificacion;
	
	/**
	 * El id de la sucursal al que pertenece
	 */
	private long idSucursal;
	
	// Constantes
	
	/**
	 * Constante que denota el estado "pendiente" de un pedido
	 */
	public static final String PENDIENTE = "PENDIENTE";
	
	/**
	 * Constante que denota el estado "entregado" de un pedido
	 */
	public static final String ENTREGADO = "ENTREGADO";
	

	/**
	 * Constructor de OrdenPedido con valores
	 * @param id
	 * @param precio
	 * @param fechaEsperada
	 * @param fechaLlegada
	 * @param estado
	 * @param calificacion
	 */
	public Orden(long id, Double precio, Timestamp fechaEsperada, Timestamp fechaLlegada, String estado,
			Double calificacion, long idSucursal) {
		this.id = id;
		this.precio = precio;
		this.fechaEsperada = fechaEsperada;
		this.fechaLlegada = fechaLlegada;
		this.estado = estado;
		this.calificacion = calificacion;
		this.idSucursal = idSucursal;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Orden(){
		this.id = 0;
		this.precio = 0.0;
		this.fechaEsperada = new Timestamp(0);
		this.fechaLlegada = new Timestamp(0);
		this.estado = "";
		this.calificacion = 0.0;
		this.idSucursal = 0;
	}

	/**
	 * Obtiene el identificador de la orden
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el identificador de la orden
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna el precio de la orden
	 */
	public Double getPrecio() {
		return precio;
	}

	/**
	 * Modifica el precio de la orden
	 * @param precio - nuevo precio de la orden
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	/**
	 * Retorna la fecha esperada de la orden
	 */
	public Timestamp getFechaEsperada() {
		return fechaEsperada;
	}

	/**
	 * Modifica la fecha esperada de la orden
	 * @param fechaEsperada - nueva fecha esperada de la orden
	 */
	public void setFechaEsperada(Timestamp fechaEsperada) {
		this.fechaEsperada = fechaEsperada;
	}

	/**
	 * Retorna la fecha de llegada de la orden
	 */
	public Timestamp getFechaLlegada() {
		return fechaLlegada;
	}

	/**
	 * Modifica la fecha de llegada de la orden
	 * @param fechaLlegada
	 */
	public void setFechaLlegada(Timestamp fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	/**
	 * Obtiene el estado de la orden
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Modifica el estado de la orden
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene la calificación de la orden
	 */
	public Double getCalificacion() {
		return calificacion;
	}

	/**
	 * Modifica la calificación de la orden
	 * @param calificacion - calificación de la orden
	 */
	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	/**
	 * Obtiene el identificador de la sucursal que realizó la orden
	 */
	public long getIdSucursal() {
		return idSucursal;
	}

	/**
	 * Modifica el identificador de la sucursal que realizó la orden
	 * @param idSucursal
	 */
	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public String toString() {
		return "OrdenPedido [id=" + id + ", precio=" + precio + ", fechaEsperada=" + fechaEsperada + ", fechaLlegada="
				+ fechaLlegada + ", estado=" + estado + ", calificacion=" + calificacion + "]";
	}
	
	

}
