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
	private Long id;
	
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
	public Orden(Long id, Double precio, Timestamp fechaEsperada, Timestamp fechaLlegada, String estado,
			Double calificacion) {
		this.id = id;
		this.precio = precio;
		this.fechaEsperada = fechaEsperada;
		this.fechaLlegada = fechaLlegada;
		this.estado = estado;
		this.calificacion = calificacion;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Orden(){
		this.id = (long) 0;
		this.precio = 0.0;
		this.fechaEsperada = new Timestamp(0);
		this.fechaLlegada = new Timestamp(0);
		this.estado = "";
		this.calificacion = 0.0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Timestamp getFechaEsperada() {
		return fechaEsperada;
	}

	public void setFechaEsperada(Timestamp fechaEsperada) {
		this.fechaEsperada = fechaEsperada;
	}

	public Timestamp getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Timestamp fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	@Override
	public String toString() {
		return "OrdenPedido [id=" + id + ", precio=" + precio + ", fechaEsperada=" + fechaEsperada + ", fechaLlegada="
				+ fechaLlegada + ", estado=" + estado + ", calificacion=" + calificacion + "]";
	}
	
	

}
